package cn.com.web.wzy.service.impl;

import cn.com.web.wzy.service.FilesService;
import cn.com.web.wzy.service.ex.FileInaccessibleException;
import cn.com.web.wzy.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IFilesServiceImpl implements FilesService {

    // 文件服务器路径
    @Value("${code-generator.output-path}")
    private String filePath;

    // 文件服务器基础url
    @Value("${file-server.url}")
    private String baseUrl;

    @Override
    public List<Map<String, String>> queryFiles(String path) {
        List<Map<String, String>> result = new ArrayList<>();

        // 处理路径，确保路径格式正确
        String targetPath = buildTargetPath(path);

        File directory = new File(targetPath);

        // 检查目录是否存在且可访问
        if (!directory.exists() || !directory.isDirectory()) {
            throw new FileInaccessibleException();
        }

        // 获取目录下的所有文件和文件夹
        File[] files = directory.listFiles();
        if (files == null) {
            return result; // 如果无法获取文件列表，返回空列表
        }

        // 处理每个文件/文件夹，构建返回结果
        for (File file : files) {
            Map<String, String> fileInfo = new HashMap<>(4);
            fileInfo.put("name", file.getName());
            fileInfo.put("path", getRelativePath(file));

            // 判断是文件还是文件夹
            if (file.isDirectory()) {
                fileInfo.put("type", "folder");
                fileInfo.put("url", ""); // 文件夹没有URL
            } else {
                fileInfo.put("type", "file");
                fileInfo.put("url", buildFileUrl(file)); // 构建文件的URL
            }

            result.add(fileInfo);
        }

        return result;
    }

    @Override
    public boolean deleteFile(Map<String, String> params) {
        String type = params.get("type");
        String path = Path.of(filePath, params.get("path")).toString().replaceAll("\\\\", "/");

        return switch (type) {
            case "folder" -> FileUtils.deleteDirectory(path);
            case "file" -> FileUtils.deleteFile(path);
            default -> false;
        };
    }

    /**
     * 构建目标路径
     * @param path 路径
     * @return 完整路径
     */
    private String buildTargetPath(String path) {
        // 处理根路径情况
        if (path == null || path.trim().isEmpty()) {
            return filePath;
        }

        return Path.of(filePath, path).toString().replaceAll("\\\\", "/");
    }

    /**
     * 获取文件相对路径（以/开头）
     */
    private String getRelativePath(File file) {
        try {
            String absoluteFilePath = filePath.endsWith(File.separator)
                    ? filePath
                    : filePath + File.separator;

            String relativePath = file.getAbsolutePath().substring(absoluteFilePath.length());
            // 将文件分隔符统一替换为/
            return "/" + relativePath.replace(File.separator, "/");
        } catch (Exception e) {
            return "/";
        }
    }

    /**
     * 构建文件的URL
     */
    private String buildFileUrl(File file) {
        try {
            String absoluteFilePath = filePath.endsWith(File.separator)
                    ? filePath
                    : filePath + File.separator;

            String relativePath = file.getAbsolutePath().substring(absoluteFilePath.length());
            // 确保基础URL以/结尾
            String url = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
            // 拼接URL和相对路径，使用/作为分隔符
            return url + relativePath.replace(File.separator, "/");
        } catch (Exception e) {
            return baseUrl;
        }
    }
}