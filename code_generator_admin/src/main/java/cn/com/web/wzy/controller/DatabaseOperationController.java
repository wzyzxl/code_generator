package cn.com.web.wzy.controller;

import cn.com.web.wzy.entity.ResponseBodyEntity;
import cn.com.web.wzy.service.DatabaseService;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据库相关操作controller层
 */
@RestController
@RequestMapping("/database")
public class DatabaseOperationController extends BaseController {
    private final DatabaseService databaseService;

    public DatabaseOperationController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * 获取数据库类型
     *
     * @return 数据类型列表
     */
    @GetMapping("/types")
    public ResponseBodyEntity<List<Map<String, String>>> types() {
        return ResponseBodyEntity.ok(databaseService.types());
    }

    /**
     * 测试连接
     *
     * @param requestVo 请求
     * @return 连接情况
     */
    @PostMapping("/connections")
    public ResponseBodyEntity<Map<String, Object>> connections(@RequestBody RequestVo requestVo) {
        return ResponseBodyEntity.ok(databaseService.connections(requestVo));
    }

    /**
     * 获取模式
     *
     * @param requestVo 请求
     * @return mos
     */
    @PostMapping("/patterns")
    public ResponseBodyEntity<List<String>> patterns(@RequestBody RequestVo requestVo) {
        return ResponseBodyEntity.ok(databaseService.patterns(requestVo));
    }

    /**
     * 获取表名
     *
     * @param requestVo 请求
     * @return 表名
     */
    @PostMapping("/tables")
    public ResponseBodyEntity<List<String>> tables(@RequestBody RequestVo requestVo) {
        return ResponseBodyEntity.ok(databaseService.tables(requestVo));
    }

    /**
     * 保存配置
     *
     * @param requestVo 请求
     * @return 保存状态
     */
    @PostMapping("/save")
    public ResponseBodyEntity<Map<String, Object>> save(@RequestBody RequestVo requestVo) {
        return ResponseBodyEntity.ok(databaseService.save(requestVo));
    }
}