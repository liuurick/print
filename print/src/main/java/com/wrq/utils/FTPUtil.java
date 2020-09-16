package com.wrq.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.List;

/**
 *  接受List集合，把list上传到自定义的ftp服务器上面去
 */
@Slf4j
public class FTPUtil {

    private  static  String ftpServerIp = "127.0.0.1";
    private  static  String ftpUser = "wangqian";
    private  static  String ftpPass = "wangqian";


    public  FTPUtil(String ip,int port,String user,String pwd){
        this.ip     =  ip;
        this.port   =  port;
        this.user   = user;
        this.pwd    = pwd;
    }

    //对面开放的upload方法
    public static boolean uploadFile(List<File> fileList, String fileLocation) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpServerIp,21,ftpUser,ftpPass);
        log.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile(fileLocation, fileList);
        log.info("开始连接ftp服务器，结束上传，上传结果:{}",result);
        return result;
    }

    //对面开放的upload方法
    public static boolean downloadFile(String remotePath, String newFileName, String targetFilePath) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpServerIp,21,ftpUser,ftpPass);
        log.info("开始连接ftp服务器");
        boolean result = ftpUtil.download(remotePath, newFileName, targetFilePath);
        log.info("开始连接ftp服务器，结束下载，下载结果:{}",result);
        return result;
    }

    private  boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;

        if(connectServer(this.ip,this.port,this.user,this.pwd)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for(File fileItem : fileList){
                    fis  = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);
                }
            } catch (IOException e) {
                log.error("上传文件到ftp服务器异常",e);
                uploaded  = false;
                e.printStackTrace();
            }finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return  uploaded;
    }

    private boolean  download(String remotePath, String newFileName, String targetFilePath) throws IOException {
        boolean download = true;
        FileOutputStream fos = null;

        if(connectServer(this.ip,this.port,this.user,this.pwd)){
            try {

            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            //设置要下载到的目录： "/upload/54848484.doc"
            File localFile = new File(targetFilePath);
            //得到输出
            fos = new FileOutputStream(localFile);
            ftpClient.retrieveFile(newFileName, fos); //开始下载文件

            } catch (IOException e) {
                log.error("从ftp服务器下载文件异常",e);
                download  = false;
                e.printStackTrace();
            }finally {
                fos.close();
                ftpClient.disconnect();
            }
        }
        return  download;
    }

    //连接ftp
    private boolean connectServer(String ip,int port,String user,String pwd){
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user,pwd);
        } catch (IOException e){
            log.error("连接FTP服务器异常",e);
            e.printStackTrace();
        }
        return  isSuccess;
    }

    private  String ip;
    private  int port;
    private  String user;
    private  String pwd;
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
