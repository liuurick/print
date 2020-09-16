package com.wrq.service;

import com.wrq.commons.ServerResponse;
import com.wrq.pojo.User;
import com.wrq.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by wangqian on 2019/4/6.
 */
public interface IFileService {

    ServerResponse<FileVo> upload(MultipartFile file, String path, Integer userId);

    ServerResponse uploadImg(MultipartFile file, String path);

    ServerResponse userDownload(String path, String file,Integer userId, HttpServletResponse response) throws UnsupportedEncodingException;

    ServerResponse backendDownload(String path, String file, String orderNo, HttpServletResponse response) throws UnsupportedEncodingException;

    ServerResponse shareDownload(String path, String file, String viewName, HttpServletResponse response) throws UnsupportedEncodingException;

    ServerResponse download ( String path, String file, HttpServletResponse response, String viewName )throws UnsupportedEncodingException;

    ServerResponse getFileList(Integer userId,Integer pageNum, Integer pageSize);

    ServerResponse getNotShareFileList(Integer userId);

    String uploadRichImg(MultipartFile file,String path);

}
