package com.newland.balbaxmx.layered.simple.common;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.NativeStorage;
import de.innosystec.unrar.rarfile.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author: zhangyh
 * @ClassName: FileCompressUtil
 * @Date: 2020/5/1 9:27
 * @Operation:
 * @Description: 文件压缩工具
 */
public class FileCompressUtil {

    private static Logger logger = LoggerFactory.getLogger(FileCompressUtil.class);
    /**
     * 设置缓冲区大小4M
     **/
    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    public static String FILE_TYPE_ZIP = "504B0304";
    public static String FILE_TYPE_RAR = "52617221";


    /**
     * 解压
     *
     * @param path 压缩文件
     * @param destDirPath 解压路径
     * @return 文件所有路径
     */
    public static List<String> compress(String path, String destDirPath) {
        List<String> info = new ArrayList<>();
        try {
            if (path != null && !"".equals(path)) {
                String fielName = path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf("."));
                String fileTypeHex = getFileByFile(new File(path));
                if (FILE_TYPE_ZIP.equals(fileTypeHex)) {
                    info = FileCompressUtil.unZip(new File(path), destDirPath, fielName);
                } else if (FILE_TYPE_RAR.equals(fileTypeHex)) {
                    logger.error("文件格式是rar,不支持rar格式");
//                    FileCompressUtil.unrar(new File(path), destDirPath, fielName);
                } else {
                    logger.error("文件格式不是zip或者rar");
                    return info;
                }
            }
        } catch (Exception e) {
            logger.error("解压文件[{}]失败[{}]", path, e);
            return info;
        }
        return info;
    }

    /**
     * @param srcFile 压缩包
     * @param destDirPath 解压文件根路径
     * @param fileName    文件夹名称
     * @throws RuntimeException
     */
    public static List<String> unZip(File srcFile, String destDirPath, String fileName) throws RuntimeException {
        long start = System.currentTimeMillis();
        List<String> allPath = new ArrayList<>();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        //删除原有文件
        String jarAllPath = destDirPath + File.separator + fileName;
        File file = new File(jarAllPath);
        if (file.exists()) {
            FileUtil.deleteDirectory(jarAllPath);
        }
        file.mkdirs();
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    String dirPath = jarAllPath + File.separator + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                    allPath.add(dirPath);
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(jarAllPath + File.separator + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                    allPath.add(targetFile.getPath());
                }
            }
            long end = System.currentTimeMillis();
            logger.debug("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return allPath;
    }


    /**
     * 解压rar
     *
     * @param sourceRar   需要解压的rar文件
     * @param destDirPath 需要解压到的文件目录
     * @param fielName    文件夹名称
     * @throws Exception
     */
    public static void unrar(File sourceRar, String destDirPath, String fielName) throws Exception {
        //删除原有文件
        String jarAllPath = destDirPath + File.separator + fielName;
        if (new File(jarAllPath).exists()) {
            FileUtil.deleteDirectory(jarAllPath);
        }
        File destDir = new File(jarAllPath);
        Archive archive = null;
        FileOutputStream fos = null;
        try {
            archive = new Archive(new NativeStorage(sourceRar));
            FileHeader fh = archive.nextFileHeader();
            File destFileName = null;
            while (fh != null) {
                String compressFileName = fh.getFileNameString().trim();
                destFileName = new File(destDir.getAbsolutePath() + File.separator + compressFileName);
                if (fh.isDirectory()) {
                    if (!destFileName.exists()) {
                        destFileName.mkdirs();
                    }
                    fh = archive.nextFileHeader();
                    continue;
                }
                if (!destFileName.getParentFile().exists()) {
                    destFileName.getParentFile().mkdirs();
                }
                fos = new FileOutputStream(destFileName);
                archive.extractFile(fh, fos);
                fos.close();
                fos = null;
                fh = archive.nextFileHeader();
            }
            archive.close();
            archive = null;
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
            if (archive != null) {
                try {
                    archive.close();
                } catch (Exception e) {
                }
            }
        }
    }


    /**
     * 判断文件是否是zip或者rar格式
     *
     * @param file
     * @return
     */
    public static String getFileByFile(File file) {
        String filetype = "";
        byte[] b = new byte[50];
        try {
            InputStream is = new FileInputStream(file);
            is.read(b);
            filetype = getFileTypeByStream(b);
            is.close();
        } catch (FileNotFoundException e) {
            logger.error("文件读取类型失败[{}]", e);
        } catch (IOException e) {
            logger.error("文件读取类型失败2[{}]", e);
        }
        filetype = filetype.substring(0, 8);
        return filetype.toUpperCase();
    }

    /**
     * 判断文件类型
     *
     * @param b
     * @return
     */
    public static String getFileTypeByStream(byte[] b) {
        byte[] head = Arrays.copyOf(b,50);
        String filetype = String.valueOf(getFileHexString(head));
        filetype = filetype.substring(0, 8);
        return filetype.toUpperCase();
    }

    /**
     * 类型转16进制
     *
     * @param b
     * @return
     */
    public static String getFileHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
