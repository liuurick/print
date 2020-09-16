package com.wrq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.wrq.commons.ServerResponse;
import com.wrq.config.ParameterConfig;
import com.wrq.dao.FileMapper;
import com.wrq.dao.ScoreMapper;
import com.wrq.dao.ShareMapper;
import com.wrq.dao.UserMapper;
import com.wrq.enums.*;
import com.wrq.exception.PrintException;
import com.wrq.form.ShareCreateForm;
import com.wrq.pojo.*;
import com.wrq.service.IFileService;
import com.wrq.service.IShareService;
import com.wrq.utils.DateTimeUtil;
import com.wrq.utils.EnumUtil;
import com.wrq.utils.ShareScoreUtil;
import com.wrq.vo.ShareDetailVo;
import com.wrq.vo.ShareListVo;
import com.wrq.vo.ShopVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangqian on 2019/4/21.
 */
@Service(value = "iShareService")
@Slf4j
public class ShareServiceImpl implements IShareService {


    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ParameterConfig parameterConfig;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private IFileService iFileService;


    @Override
    public ServerResponse insertShareRecode(ShareCreateForm shareCreateForm, User user) {

        File file = fileMapper.selectByPrimaryKey(shareCreateForm.getFileId());

        if ( file == null ){
            return ServerResponse.createByErrorMessage("找不到需要分享的文件，请重新上传！");
        }

        // 1. 设置积分

        int score = ShareScoreUtil.getScore(file.getPageNum());

        // 2.写入数据库

        int result = fileMapper.updateFileScoreAndStatus(shareCreateForm.getFileId(), score);

        if ( result <= 0 ){
            return ServerResponse.createByErrorMessage("写入文件积分失败，请稍后再试！");
        }

        // 3. share表
        Share share = new Share();


        share.setUserId(user.getId());
        share.setTitle(shareCreateForm.getTitle());
        share.setDescription(shareCreateForm.getDesc());
        share.setViewNum("1");
        share.setContent(shareCreateForm.getRichText());
        share.setDownloadNum(0);
        share.setFileId(shareCreateForm.getFileId());
        share.setIsDelete(ShareStatusEnum.NOT_DELETE.getCode());
        share.setIsHot(ShareStatusEnum.NOT_HOT.getCode());
        share.setIsTop(ShareStatusEnum.NOT_TOP.getCode());
        share.setTag(String.valueOf( EnumUtil.getByCode(shareCreateForm.getTagValue(), TagNameEnum.class).getCode() ));

        int insert = shareMapper.insert(share);

        if ( insert <= 0 ){
            return ServerResponse.createByErrorMessage("创建分享失败，请稍后再试！");
        }

        return ServerResponse.createBySuccessMessage("分享成功！");
    }

    /**
     * 获取全部分享的列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse getShareList(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        List<Share> shareList = shareMapper.selectShareList();

        List<ShareListVo> shareListVoList = assembleShareListVo(shareList);

        PageInfo pageResult = new PageInfo(shareList);

        pageResult.setList(shareListVoList);

        return  ServerResponse.createBySuccess(pageResult);
    }


    private List<ShareListVo> assembleShareListVo(List<Share> shareList){

        List<ShareListVo> shareListVo = Lists.newArrayList();

        for(Share share : shareList){

            ShareListVo shareVo = new ShareListVo();

            shareVo.setTitle(share.getTitle());
            shareVo.setCreateTime(DateTimeUtil.dateToStr(share.getCreateTime(), "yyyy-MM-dd"));
            shareVo.setDesc(share.getDescription());

            Integer userId = share.getUserId();

            User user = userMapper.selectByPrimaryKey(userId);

            if ( user == null ){
                shareVo.setHeadImg("");
            }else {
                shareVo.setHeadImg(parameterConfig.getImageHost() + user.getHeaderPic());
            }

            File file = fileMapper.selectByPrimaryKey(share.getFileId());

            shareVo.setShareId(share.getId());
            shareVo.setIntegral(file.getIntegral());
            shareVo.setTagName(EnumUtil.getByCode(Integer.parseInt(share.getTag()), TagNameEnum.class).getMessage());
            shareVo.setUsername(user.getUsername());
            shareVo.setViewNum(share.getViewNum());

            shareListVo.add(shareVo);
        }
        return shareListVo;
    }

    /**
     * 获取分享详情
     * @param shareId
     * @return
     */
    @Override
    public ServerResponse getShareDetailById(Integer shareId, User currentUser) {

        Share share = shareMapper.selectByPrimaryKeyAndNotDelete(shareId);

        if ( share == null ){
            return ServerResponse.createByErrorMessage("此分享不存在，或者刚刚删除~");
        }

        Integer fileId = share.getFileId();

        File file = fileMapper.selectByPrimaryKey(fileId);

        if ( file == null ){
            return ServerResponse.createByErrorMessage("待分享文件找不到啦~");
        }

        String fileExtensionName = file.getFileName().substring(file.getFileName().lastIndexOf(".")+1);

        User user = userMapper.selectByPrimaryKey(share.getUserId());

        if ( user == null ){
            return ServerResponse.createByErrorMessage("提供文件的小伙伴找不啦~");
        }


        /* view + 1 */

        int result = shareMapper.addViewNumByShareId(shareId);

        if ( result <= 0 ){
            log.error(" 分享查看次数加一时出现错误 ");
        }

        ShareDetailVo shareDetailVo = new ShareDetailVo();

        shareDetailVo.setFileType(fileExtensionName);
        shareDetailVo.setTitle(share.getTitle());
        shareDetailVo.setUsername(user.getUsername());
        shareDetailVo.setContent(share.getContent());
        shareDetailVo.setCreateTime(share.getCreateTime());
        shareDetailVo.setDesc(share.getDescription());
        shareDetailVo.setDownloadNum(share.getDownloadNum());
        shareDetailVo.setFileId(share.getFileId());
        shareDetailVo.setShareId(shareId);
        shareDetailVo.setFileTypeImg( parameterConfig.getImageHost() + fileExtensionName + ".png" );
        shareDetailVo.setIntegral(file.getIntegral());
        shareDetailVo.setPageNum(file.getPageNum());
        shareDetailVo.setTagName(EnumUtil.getByCode(Integer.parseInt(share.getTag()), TagNameEnum.class).getMessage());

        User goDownloadUser = userMapper.selectByPrimaryKey(currentUser.getId());

        shareDetailVo.setMyIntegral(goDownloadUser.getIntegral());

        return ServerResponse.createBySuccess(shareDetailVo);
    }


    /**
     * 进行预下载
     * @param shareId
     * @param user
     * @return
     */
    public ServerResponse prepareForDownload(Integer shareId, User user) {

        Share share = shareMapper.selectByPrimaryKeyAndNotDelete(shareId);

        /* 1. 判断分享存在否，自己分享的？ */
        if ( share == null ){
            log.error(" [分享下载] 待下载的分享已被删除");
            return ServerResponse.createByErrorMessage("待下载的分享已被删除！");
        }


        User currentUser = userMapper.selectByPrimaryKey(user.getId());


        if ( share.getUserId() == user.getId() ){
            log.error(" [分享下载] 不可以下载自己的文件哦");
            return ServerResponse.createByErrorMessage("不可兑换自己上传的文件！");
        }


        /* 2. 判断文件存在否，以及文件的 share 状态 */
        Integer fileId = share.getFileId();

        File file = fileMapper.selectByPrimaryKey(fileId);

        if ( file == null ){
            log.error(" [分享下载] 待下载的文件找不到啦");
            return ServerResponse.createByErrorMessage("待下载的文件找不到啦~");
        }

        /* 是否已经下载过？ */
        Score scoreRecord = scoreMapper.selectByUserIdAndShareId(user.getId(), shareId);
        if ( scoreRecord != null ){
            log.error(" [分享下载] 已经下载过此文件");
            return ServerResponse.createByErrorMessage("已经兑换过此文件，请到个人中心查看~");
        }

        if ( ShareEnum.NOT_SHARE.getCode() == file.getShare() ){
            log.error(" [分享下载] 文件的主人取消此文件的分享啦");
            return ServerResponse.createByErrorMessage("来晚啦，文件的主人取消分享啦~");
        }


        Integer integral = file.getIntegral();

        /* 3. 判断当前用户积分是否够 */

        if (  Integer.parseInt(currentUser.getIntegral()) < integral ){
            log.error(" [分享下载] 您的积分余额不足");
            return ServerResponse.createByErrorMessage("您的积分余额不足~");
        }

        return ServerResponse.createBySuccess("可以下载");
    }

    /**
     * 用户中心 积分记录下载文件
     * @param path
     * @param id
     * @param userId
     * @param response
     * @return
     */
    @Override
    public ServerResponse downloadForUserCenter(String path, Integer id, Integer userId, HttpServletResponse response) {


        Score score = scoreMapper.selectByPrimaryKey(id);

        if ( score == null ){
            return ServerResponse.createByErrorMessage("无此交易记录~");
        }

        if ( userId != score.getUserId() ){
            return ServerResponse.createByErrorMessage("此交易记录不是当前用户，请核实");
        }

        String fileNewName = score.getFileNewName();

        ServerResponse download = null;
        try {
            download = iFileService.download(path, fileNewName, response, fileNewName);
        } catch (UnsupportedEncodingException e) {
            log.error("编码失败");
        }

        if ( !download.isSuccess() ){
            return ServerResponse.createByErrorMessage("下载失败");
        }

        return ServerResponse.createBySuccess();
    }


    /**
     * 进行下载
     * @param path
     * @param shareId
     * @param user
     * @param response
     * @return
     */
    @Override
    @Transactional
    public ServerResponse downloadShareByShopId(String path, Integer shareId, User user, HttpServletResponse response) {

        Share share = shareMapper.selectByPrimaryKeyAndNotDelete(shareId);

        Integer fileId = share.getFileId();

        File file = fileMapper.selectByPrimaryKey(fileId);

        Integer integral = file.getIntegral();

        User currentUser = userMapper.selectByPrimaryKey(user.getId());

        /* 4. 更改两人积分数、下载数 */

        Integer userId = share.getUserId();

        User fileOwner = userMapper.selectByPrimaryKey(userId);

        if ( fileOwner != null ){

            Integer newIntegral = Integer.parseInt(fileOwner.getIntegral()) + integral;

            int result = userMapper.updateByPrimaryKeyAndIntegral(fileOwner.getId(), newIntegral.toString());

            if ( result <= 0 ){
                log.error(" [下载分享] 更新文件主积分时出现错误 ");
                throw new PrintException(ResultEnum.UPRATE_FILE_OWNER_INTEGRAL_ERROR);
            }

        }else{
            log.error(" 找不到此创建此分享的用户记录！ ");
        }

        Integer newIntegral = Integer.parseInt(currentUser.getIntegral()) - integral;

        int result = userMapper.updateByPrimaryKeyAndIntegral(currentUser.getId(), newIntegral.toString());

        if ( result <= 0 ){
            log.error(" [下载分享] 更新积分操作时出现错误 ");
            throw new PrintException(ResultEnum.UPRATE_CURRENT_USER_INTEGRAL_ERROR);
        }

        Integer downloadNum = share.getDownloadNum() + 1;

        Share newShare = new Share();
        newShare.setDownloadNum( downloadNum );
        newShare.setId(shareId);
        int updateResult = shareMapper.updateByPrimaryKeySelective(newShare);

        if ( updateResult <= 0 ){
            log.error(" [下载分享] 更新下载数时出现错误 ");
            throw new PrintException(ResultEnum.UPRATE_DOWNLOAD_NUMBER_ERROR);
        }

        /* 5. score 历史记录 */

        Score score = new Score();

        score.setShareId(shareId);
        score.setIntegral(integral);
        score.setFileNewName(file.getNewName());
        score.setLookOver(ScoreEnum.NOT_LOOK.getCode());
        score.setOwnerId(userId);
        score.setUserId(currentUser.getId());

        int insert = scoreMapper.insert(score);

        if ( insert <= 0 ){
            log.error(" [下载分享] 添加积分记录时出现错误 ");
            throw new PrintException(ResultEnum.ADD_SCORE_RECORD_ERROR);
        }

        /* 6. 下载  */

        try {
            ServerResponse serverResponse = iFileService.shareDownload(path, file.getNewName(), file.getFileName(), response);
            if ( !serverResponse.isSuccess() ){
                log.error(" [下载分享] 下载失败 ");
                throw new PrintException(ResultEnum.FILE_DOWNLOAD_ERROR);
            }

        } catch (UnsupportedEncodingException e) {
            log.error("[文件下载] 文件下载是名字转码出现错误，e = {}",e);
        }

        return ServerResponse.createBySuccess();
    }


    @Override
    public ServerResponse getShareListTypeSort(int pageNum, int pageSize, int type) {

        PageHelper.startPage(pageNum,pageSize);

        List<Share> shareList = shareMapper.selectShareListTypeSort(type);

        List<ShareListVo> shareListVoList = assembleShareListVo(shareList);

        PageInfo pageResult = new PageInfo(shareList);

        pageResult.setList(shareListVoList);

        return  ServerResponse.createBySuccess(pageResult);
    }


}
