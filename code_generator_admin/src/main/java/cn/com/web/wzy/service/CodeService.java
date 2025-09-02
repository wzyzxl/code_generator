package cn.com.web.wzy.service;

import cn.com.web.wzy.vo.RequestVo;

import java.util.List;
import java.util.Map;

/**
 * 代码处理相关接口
 */
public interface CodeService {
    /**
     * 获取文件名
     *
     * @param requestVo 请求
     * @return 文件名
     */
    public List<String> filenames(RequestVo requestVo);

    /**
     * 获取代码内容
     *
     * @param requestVo 请求
     * @return 代码
     */
    public Map<String, String> content(RequestVo requestVo);

    /**
     * 下载代码
     *
     * @param requestVo 请求
     * @return 下载链接
     */
    public Map<String, String> download(RequestVo requestVo);
}
