package cn.com.web.wzy.utils;

import cn.com.web.wzy.service.ex.FileGenerationErrorException;
import cn.com.web.wzy.service.ex.FileNotFoundException;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件相关工具类
 */
public class FileUtils {
    /**
     * 将多个文件压缩到指定ZIP文件，并删除原文件
     *
     * @param filePaths   要压缩的文件路径列表
     * @param zipFilePath 生成的ZIP文件路径
     * @return 生成的ZIP文件路径
     * @throws IOException 当IO操作失败时抛出
     */
    public static String compressFilesToZip(List<String> filePaths, String zipFilePath) throws IOException {
        // 验证参数
        if (filePaths == null || filePaths.isEmpty()) {
            throw new FileGenerationErrorException("文件路径列表不能为空");
        }
        if (zipFilePath == null || zipFilePath.trim().isEmpty()) {
            throw new FileGenerationErrorException("ZIP文件路径不能为空");
        }

        // 创建ZIP文件输出流
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            // 遍历所有文件路径，添加到ZIP中
            for (String filePath : filePaths) {
                File file = new File(filePath);
                // 只处理存在的文件
                if (file.exists() && file.isFile()) {
                    addFileToZip(zos, file, "");
                }
            }
        }

        // 压缩完成后删除原文件
        deleteFiles(filePaths);

        return zipFilePath;
    }

    /**
     * 删除文件
     *
     * @param filePaths 待删除文件的路径（包含文件名）
     */
    public static void deleteFiles(List<String> filePaths) {
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                // 保存文件所在目录，用于后续检查
                File parentDir = file.getParentFile();

                // 删除文件
                boolean deleted = file.delete();
                if (deleted) {
                    // 检查并删除空目录
                    deleteEmptyDirs(parentDir);
                } else {
                    System.err.println("无法删除文件: " + filePath);
                }
            }
        }
    }

    /**
     * 删除指定路径的文件
     *
     * @param filePath 要删除的文件路径
     * @return 如果文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        // 删除文件
        return file.delete();
    }

    /**
     * 删除指定路径的文件夹及其所有内容
     *
     * @param directoryPath 要删除的文件夹路径
     * @return 如果文件夹删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String directoryPath){
        File directory = new File(directoryPath);

        // 检查文件夹是否存在
        if (!directory.exists()) {
            throw new FileNotFoundException();
        }

        // 递归删除文件夹内的所有文件和子文件夹
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归删除子文件夹
                    deleteDirectory(file.getAbsolutePath());
                } else {
                    // 删除文件
                    if (!file.delete()) {
                        return false;
                    }
                }
            }
        }

        // 删除空文件夹
        return directory.delete();
    }

    /**
     * 将单个文件添加到ZIP输出流
     *
     * @param zos       zip输出文件流
     * @param file      文件
     * @param parentDir 父路径
     * @throws IOException 文件异常
     */
    private static void addFileToZip(ZipOutputStream zos, File file, String parentDir) throws IOException {
        // 创建ZIP条目
        String entryName = parentDir + file.getName();
        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);

        // 写入文件内容
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024 * 4];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
        }

        zos.closeEntry();
    }

    /**
     * 递归删除空文件夹
     *
     * @param dir 要检查的目录
     */
    private static void deleteEmptyDirs(File dir) {
        // 目录不存在则直接返回
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }

        // 检查目录是否为空
        File[] files = dir.listFiles();
        if (files != null && files.length == 0) {
            // 目录为空，删除当前目录
            boolean deleted = dir.delete();
            if (deleted) {
                // 递归检查父目录
                deleteEmptyDirs(dir.getParentFile());
            } else {
                System.err.println("无法删除空目录: " + dir.getAbsolutePath());
            }
        }
    }
}