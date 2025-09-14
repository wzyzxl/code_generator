package cn.com.web.wzy.service;

import java.util.List;
import java.util.Map;

/**
 * 文件相关接口
 */
public interface FilesService {
    /**
     * 查询文件
     * @return 文件列表
     */
    List<Map<String, String>> queryFiles(String path);

    /**
     * 删除文件
     * @param params 参数，包含文件类型和文件路径
     */
    boolean deleteFile(Map<String, String> params);
}
