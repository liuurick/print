package com.wrq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.google.common.collect.Lists;
import com.wrq.commons.ServerResponse;
import com.wrq.config.ParameterConfig;
import com.wrq.dao.FileMapper;
import com.wrq.dao.ShopMapper;
import com.wrq.enums.OrderStatusEnum;
import com.wrq.enums.ShareEnum;
import com.wrq.pojo.OrderMaster;
import com.wrq.pojo.Shop;
import com.wrq.service.IFileService;
import com.wrq.service.IOrderService;
import com.wrq.utils.DateTimeUtil;
import com.wrq.utils.EnumUtil;
import com.wrq.utils.FTPUtil;
import com.wrq.utils.PageCountUtil;
import com.wrq.vo.FileListVo;
import com.wrq.vo.FileVo;
import com.wrq.vo.OrderListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangqian on 2019/4/6.
 */
@Slf4j
@Service(value = "iFileService")
public class FileServiceImpl implements IFileService {


    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ParameterConfig parameterConfig;

    /**
     * 文件上传
     * @param file
     * @param path
     * @return
     */
    public ServerResponse<FileVo> upload(MultipartFile file, String path, Integer userId){

        String fileName = file.getOriginalFilename();

        int pageCount = 0;

        int fileId = 0;

        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;

        log.info("文件开始上传，上传文件的文件名:{},上传的路径为:{},新文件名为:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);

        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {

            // 文件已经上传
            file.transferTo(targetFile);


            // targetFile上传到FTP服务器,file文件夹
            FTPUtil.uploadFile(Lists.newArrayList(targetFile), "file");

            //  获取文件的页数
            try {
                String uploadPath =   path.substring(path.lastIndexOf("\\") + 1);
                pageCount = getFilePageCount(uploadPath, uploadFileName, fileExtensionName);
                log.info("获取文件的页数为 pageCount = {}", pageCount);
            } catch ( Exception e ) {
                log.error("获取文件的页数错误!");
            }

            // 进行业务逻辑
            com.wrq.pojo.File uploadFile = new com.wrq.pojo.File();
            uploadFile.setUserId(userId);
            uploadFile.setFileName(fileName);
            uploadFile.setNewName(uploadFileName);
            uploadFile.setPageNum(pageCount);
            uploadFile.setShare(ShareEnum.NOT_SHARE.getCode());
            /* 返回file Id */
            fileMapper.insert(uploadFile);
            fileId = uploadFile.getId();

            // 上传完后，删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {
            log.error("文件上传异常",e);
            return null;
        }


        FileVo fileVo = new FileVo();

        fileVo.setFileName(targetFile.getName());
        fileVo.setPageNum(pageCount);
        fileVo.setFileId(fileId);

        return ServerResponse.createBySuccess(fileVo);
    }


    public ServerResponse uploadImg(MultipartFile file, String path){

        String fileName = file.getOriginalFilename();

        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;

        log.info("文件开始上传，上传文件的文件名:{},上传的路径为:{},新文件名为:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);

        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {

            // 文件已经上传
            file.transferTo(targetFile);

            // targetFile上传到FTP服务器,file文件夹
            FTPUtil.uploadFile(Lists.newArrayList(targetFile), "img");

            // 上传完后，删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {
            log.error("文件上传异常",e);
            return null;
        }

        HashMap map = new HashMap();

        map.put("img", uploadFileName);

        return ServerResponse.createBySuccess(map);
    }


    /**
     * 用户文件列表 文件下载
     * @param path upload的文件夹
     * @param file 文件UUID名字
     * @param userId 请求下载的用户
     * @param response 流处理
     * @return 文件
     * @throws UnsupportedEncodingException
     */
    public ServerResponse userDownload(String path, String file, Integer userId, HttpServletResponse response) throws UnsupportedEncodingException {

        com.wrq.pojo.File result = fileMapper.selectFileByUserIdFileNewName(userId, file);

        if ( result == null ){
            return ServerResponse.createByErrorMessage("该用户无此文件，无权访问！");
        }

        /* 进行下载 */
        ServerResponse download = download(path, file, response, result.getFileName());

        if ( !download.isSuccess() ){
            return ServerResponse.createByErrorMessage("下载失败");
        }

        return ServerResponse.createBySuccess();
    }


    /**
     * 后台点击进行下载文件
     * @param path
     * @param file
     * @param orderNo
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public ServerResponse backendDownload(String path, String file,String orderNo, HttpServletResponse response) throws UnsupportedEncodingException {

        /* 进行下载 */

        String viewName = new StringBuilder().append("订单号：").append(orderNo).toString();

        ServerResponse download = download(path, file, response, viewName);

        if ( !download.isSuccess() ){
            return ServerResponse.createByErrorMessage("下载失败");
        }

        return ServerResponse.createBySuccess();
    }

    /**
     * 分享 扣除积分后进行下载
     * @param path
     * @param file
     * @param viewName
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public ServerResponse shareDownload(String path, String file, String viewName, HttpServletResponse response) throws UnsupportedEncodingException {

        ServerResponse download = download(path, file, response, viewName);

        if ( !download.isSuccess() ){
            return ServerResponse.createByErrorMessage("下载失败");
        }

        return ServerResponse.createBySuccess();

    }


    public ServerResponse download ( String path, String file, HttpServletResponse response, String viewName )throws UnsupportedEncodingException  {

        // 被下载的文件在服务器中的路径

        String downloadFilePath = path + "/" + file;

        try {
            FTPUtil.downloadFile("file", file, downloadFilePath);
        } catch (IOException e) {
            log.error("FTP服务器文件下载失败", e);
            return ServerResponse.createByErrorMessage("从文件服务器下载文件失败");
        }

        File targetFile = new File(downloadFilePath);

        if ( targetFile.exists() ) {

            response.setContentType("application/force-download");// 设置强制下载不打开

            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(viewName, "UTF-8"));

            byte[] buffer = new byte[1024];

            FileInputStream fis = null;

            BufferedInputStream bis = null;

            try {

                fis = new FileInputStream(targetFile);

                bis = new BufferedInputStream(fis);

                OutputStream outputStream = response.getOutputStream();

                int i = bis.read(buffer);

                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }

                return ServerResponse.createBySuccess("下载成功");

            } catch (Exception e) {
                log.error("文件下载失败");
            } finally {

                if ( bis != null ) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        log.error("BufferedInputStream 关闭失败");
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        log.error(" FileInputStream 关闭失败");
                    }
                }

                targetFile.delete();
            }
        }

        return ServerResponse.createByErrorMessage("下载失败");
    }

    /**
     * 得到文件页数
     * @param filePath 路径
     * @param fileName 名字
     * @param  fileType、doc、ppt、pdf
     * @return 文件页数
     * @throws Exception
     */
    private int getFilePageCount(String filePath, String fileName, String fileType) throws Exception {

        String fileLocation = "static" + "/" + filePath + "/" + fileName;

        int pageCount = -1;

        log.info("获取文件页数，filePath = {}, fileName = {}, type = {}, fileLocation = {}",filePath, fileName, fileType, fileLocation);

        switch ( fileType ){
            case "doc":
                return PageCountUtil.getDocCount(fileLocation);
            case "docx":
                return PageCountUtil.getDocxCount(fileLocation);
            case "ppt":
                return PageCountUtil.getPPTCount(fileLocation);
            case "pdf":
                return PageCountUtil.getPdfPage(fileLocation);
            case "xlsx":
                return PageCountUtil.getExcelPageCount(fileLocation);
            case "pptx":
                return PageCountUtil.getPPTCount(fileLocation);
            default:
                return 1;
        }
    }

    /**
     * 根据用户id 获取用的文件列表，分页显示
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse getFileList(Integer userId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<com.wrq.pojo.File> fileList = fileMapper.selectFileByUserId(userId);

        List<FileListVo> result = assembleFileVoList(fileList);

        PageInfo pageResult = new PageInfo(fileList);

        pageResult.setList(result);

        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse getNotShareFileList(Integer userId) {

        List<com.wrq.pojo.File> fileList = fileMapper.selectNotShareFileByUserId(userId);

        if ( fileList.size() == 0 ){
            return ServerResponse.createByErrorMessage("当前用户还未上传过文件，请选择自行上传！");
        }

        return ServerResponse.createBySuccess(fileList);
    }

    /**
     * orderMasterList(数据库订单主表信息) -> OrderListVo集合（个人中心订单列表展示）
     * @param fileList
     * @return
     */
    private List<FileListVo> assembleFileVoList(List<com.wrq.pojo.File> fileList ){
        List<FileListVo> targetFileList = Lists.newArrayList();
        for(com.wrq.pojo.File file : fileList){

           FileListVo fileListVo = new FileListVo();

            fileListVo.setCreateTime(DateTimeUtil.dateToStr(file.getCreateTime(), "yyyy-MM-dd HH:mm"));
            fileListVo.setFileId(file.getId());

            String fileName = file.getFileName();

            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
            fileListVo.setFileTypeImg( parameterConfig.getImageHost() + fileExtensionName + ".png" );

            fileListVo.setFileName(fileName);
            fileListVo.setFileNewName(file.getNewName());
            fileListVo.setIntegral(file.getIntegral());
            fileListVo.setShare(file.getShare());

            targetFileList.add(fileListVo);
        }
        return targetFileList;
    }

    /**
     * 富文本文件上传图片
     * @param file
     * @param path
     * @return
     */
    public String uploadRichImg(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            // 文件已经上传成功了
            FTPUtil.uploadFile(Lists.newArrayList(targetFile),"img");
            // 已经targetFile上传到FTP服务器上面
            targetFile.delete();
            //上传完后，删除upload下面的文件

        } catch (IOException e) {
            return null;
        }
        return targetFile.getName();
    }


}
