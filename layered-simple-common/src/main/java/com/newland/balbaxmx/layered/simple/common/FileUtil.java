package com.newland.balbaxmx.layered.simple.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author: zhangyh
 * @ClassName: LogFileTool
 * @Date: 2020/3/24 15:28
 * @Operation:
 * @Description: 文件工具类
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String LOCAL_CHARSET = "UTF-8";

    /**
     * 写入文件
     * @param lines
     * @return
     */
    public static boolean writeFile(String filePath,List<String> lines){
        boolean a ;
        synchronized (FileUtil.class) {
            a = write(filePath,lines);
        }
        return a;
    }


    /**
     * 写入数据
     * @param path
     * @param lines
     * @return
     */
    private static boolean write(String path,List<String> lines){
        if(lines == null || lines.size() < 1){
            return true;
        }
        RandomAccessFile accessFile = null;
        FileChannel fcin = null;
        String filePath = path.substring(0,path.lastIndexOf(File.separator));
        try {
            File folder = new File(filePath);
            if(!folder.exists()){
                folder.mkdirs();
            }
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            //写入第一行
            accessFile = new RandomAccessFile(file, "rw");
            fcin = accessFile.getChannel();
            writeLine(0,lines,fcin);
        }catch (Exception e){
            return false;
        }finally {
            close(accessFile,fcin);
        }
        return true;
    }

    /**
     * 替换文件
     * 创建一个临时文件
     * 当数据都写入到临时文件
     * 删除文件
     * 临时文件重命名为文件
     * 把缓存数据写入文件
     * @param lines
     * @return
     */
    public static boolean replaceAll(List<String> lines,String path){
        File folder = new File(path);
        if(!folder.exists() && folder.isFile()){
            logger.info("文件不存在[{}]",path);
            return true;
        }
        String temporaryFilePath =path+"-temporary";
        RandomAccessFile accessFile = null;
        FileChannel fcin = null;
        try {
            logger.info("临时文件{}]",temporaryFilePath);
            File file = new File(temporaryFilePath);
            if(file.exists()){
                logger.info("临时文件存在[{}]，删除文件",temporaryFilePath);
                file.delete();
            }
            file.createNewFile();
            accessFile = new RandomAccessFile(file, "rw");
            fcin = accessFile.getChannel();
            fcin.position(fcin.size());
            writeLine(0,lines,fcin);
            //临时文件写完
            File reFile = new File(path);
            if(reFile.exists()) {
                reFile.delete();
            }
            //重命名
            close(accessFile,fcin);
            file.renameTo(reFile);
        }catch (Exception e){
            logger.error("替换失败[{}]",e);
            return false;
        }finally {
            close(accessFile,fcin);
        }
        return true;
    }


    private static void writeLine(int start,List<String> lines,FileChannel fcin){
        try {
            int len = lines.size();
            for(int i = start ;i < len ;i++ ) {
                String line = lines.get(i);
                fcin.position(fcin.size());
                fcin.write(ByteBuffer.wrap(line.getBytes(LOCAL_CHARSET)));
                if(i != len){
                    fcin.write(ByteBuffer.wrap(getLineSeparator().getBytes(LOCAL_CHARSET)));
                }
            }
        }catch (Exception e){
            logger.error("写入数据失败[{}]",e);
        }
    }

    /**
     * 按行读取文件
     * 行号，内容
     * @param path
     * @return
     */
    public static Map<Integer,String> read(String path){
        Map<Integer,String> result = new HashMap<>();
        File file = new File(path);
        if(!file.exists()){
            logger.info("[{}]文件不存在",path);
            return result;
        }
        Path path1 = Paths.get(path);
        try (BufferedReader reader = Files.newBufferedReader(path1,Charset.forName(LOCAL_CHARSET))){
            String line = null;
            Integer lineNum = 1;
            while ((line = reader.readLine()) != null) {
                result.put(lineNum,line);
                lineNum++;
            }
        }catch (Exception e){
            return result;
        }
        return result;
    }

    /**
     * 关闭接口
     * @param closeables
     */
    public static void close(Closeable... closeables){
        for(Closeable closeable : closeables){
            if (closeable != null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取换行符
     * @return
     */
    public static String getLineSeparator(){
        return System.getProperty("line.separator");
    }


    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            logger.debug("删除目录失败：{}不存在！",dir);
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            logger.debug("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.debug("删除目录{}成功！",dir);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.debug("删除文件失败:{}不存在！",fileName);
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.debug("删除单个文件{}成功！",fileName);
                return true;
            } else {
                logger.debug("删除单个{}文件失败！",fileName);
                return false;
            }
        } else {
            logger.error("删除单个文件失败：{}不存在！",fileName);
            return false;
        }
    }


    /**
     * 下载zip文件到本地
     * @param bytes
     * @param localBashPath
     * @param packName
     * @param className
     * @param version
     * @return
     */
    public static String upLoadFile(byte[] bytes,String localBashPath,String packName, String className, String version) {
        String fileTypeHex = FileCompressUtil.getFileTypeByStream(bytes);
        String fileType ;
        if (FileCompressUtil.FILE_TYPE_ZIP.equals(fileTypeHex)) {
            fileType = ".zip";
        } else if (FileCompressUtil.FILE_TYPE_RAR.equals(fileTypeHex)) {
            fileType = ".rar";
        } else {
            logger.error("文件格式不是zip或者rar");
            return "";
        }
        String newPackName = packName.replaceAll("\\.", "-");
        String allFileName = new StringBuilder(localBashPath).append(File.separator)
                .append(newPackName).append("-")
                .append(className).append("-")
                .append(version).append(fileType).toString();
        //3.写入
        FileOutputStream outputStream = null;
        try{
            File file = new File(allFileName);
            logger.debug("判断已经存在文件{}",file.exists());
            if (file.exists()) {
                file.delete();
                logger.debug("删除已经存在文件");
            }
            File mkdir = new File(localBashPath);
            logger.debug("判断是否存在根文件夹{}",mkdir.exists());
            if(!mkdir.exists()){
                mkdir.mkdirs();
                logger.debug("创建根文件夹");
            }
            file.createNewFile();
            logger.debug("创建空文件{}，准备写入流",allFileName);
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            logger.debug("流写入文件{}成功",allFileName);
        } catch (FileNotFoundException e) {
            logger.error("文件下载本地失败[{}]",e);
            return "";
        } catch (IOException e) {
            logger.error("文件下载本地失败[{}]",e);
            return "";
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return allFileName;
    }
}
