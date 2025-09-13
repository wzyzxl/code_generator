package cn.com.web.wzy.service;

import cn.com.web.wzy.vo.RequestVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作Service层
 */
public interface DatabaseService {
    /**
     * 获取数据库类型
     *
     * @return 数据类型列表
     */
    public List<Map<String, String>> types();

    /**
     * 测试连接
     *
     * @param requestVo 请求
     * @return 连接情况
     */
    public Map<String, Object> connections(@RequestBody RequestVo requestVo);

    /**
     * 获取模式
     *
     * @param requestVo 请求
     * @return mos
     */
    public List<String> patterns(@RequestBody RequestVo requestVo);

    /**
     * 获取表名
     *
     * @param requestVo 请求
     * @return 表名
     */
    public List<String> tables(@RequestBody RequestVo requestVo);

    /**
     * 保存配置
     *
     * @param requestVo 请求
     * @return 保存状态
     */
    public Map<String, Object> save(@RequestBody RequestVo requestVo);
}
