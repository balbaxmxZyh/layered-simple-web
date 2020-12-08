package com.newland.balbaxmx.layered.simple.common;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author: zhangyh
 * @ClassName: FtpUtil
 * @Date: 2020/3/3 11:03
 * @Operation:
 * @Description: 远程文件下载本地
 */
public class FtpUtil {
    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 本地字符编码
     **/
    private static String localCharset = "GBK";

    /**
     * FTP协议里面，规定文件名编码为iso-8859-1
     **/
    private static String serverCharset = "ISO-8859-1";

    /**
     * UTF-8字符编码
     **/
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * OPTS UTF8字符串常量
     **/
    private static final String OPTS_UTF8 = "OPTS UTF8";

    /**
     * 设置缓冲区大小4M
     **/
    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    /**
     * ftp 下载文件
     * @param host
     * @param userName
     * @param pwd
     * @param port
     * @param path
     * @param localMkdir 本地文件夹
     * @return 本地文件路径，null：失败
     */
    public static String downloadRemoteFile(String host,String userName,String pwd,int
            port,String path,String localMkdir){
        String localPath = null;
        File mkdir = new File(localMkdir);
        if (mkdir.isDirectory()) {
            logger.error("本地路径不是文件夹[{}]",localMkdir);
            return localPath;
        }
        FTPClient ftpClient = null;
        try {
            ftpClient = login(host,port,userName,pwd);
            String basePath = path.substring(0,path.lastIndexOf("/"));
            String fileName = path.substring(path.lastIndexOf("/")+1,path.length());
            String serverBasePath = changeEncoding(basePath,ftpClient);
            // 判断是否存在该目录
            if (!ftpClient.changeWorkingDirectory(serverBasePath)) {
                logger.error("[{}]找不到该目录",path);
                return localPath;
            }
            // 设置被动模式，开通一个端口来传输数据
            ftpClient.enterLocalPassiveMode();
            String[] fs = ftpClient.listNames();
            // 判断该目录下是否有文件
            if (fs == null || fs.length == 0) {
                logger.error("[{}]找不到该目录",path);
                return localPath;
            }
            for (String ff : fs) {
                if(!ff.equals(fileName)){
                    continue;
                }
                if (mkdir.exists()) {
                    mkdir.mkdirs();
                }
                localPath = localMkdir + PathUtil.getSeparator() + fileName;
                File file = new File(localPath);
                if (!file.exists()) {
                   file.createNewFile();
                }
                //3.写入
                try (FileOutputStream outputStream = new FileOutputStream(file);
                     ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                    ftpClient.changeWorkingDirectory(basePath);
                    InputStream inputStream = ftpClient.retrieveFileStream(path);
                    byte[] buffer = new byte[1024];
                    int num = 0;
                    while ((num = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, num);
                    }
                } catch (Exception e) {
                    logger.error("文件下载是吧[{}]", e);
                }
            }
        }catch (Exception e){
            logger.error("[{}:{}获取文件[{}]失败]",host,port,path);
            return null;
        }finally {
            if(ftpClient != null){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }


    /**
     * ftp登陆
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    private static FTPClient login(String host, int port, String username, String password) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //限制缓冲区大小
            ftpClient.setBufferSize(BUFFER_SIZE);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.error("FTP服务器连接失败");
            }
        } catch (Exception e) {
            logger.error("FTP登录失败", e);
        }
        return ftpClient;
    }

    /**
     * FTP服务器路径编码转换
     *
     * @param ftpPath FTP服务器路径
     * @return String
     */
    private static String changeEncoding(String ftpPath,FTPClient ftpClient) {
        String directory = null;
        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                localCharset = CHARSET_UTF8;
            }
            directory = new String(ftpPath.getBytes(localCharset), serverCharset);
        } catch (Exception e) {
            logger.error("路径编码转换失败", e);
        }
        return directory;
    }

}
