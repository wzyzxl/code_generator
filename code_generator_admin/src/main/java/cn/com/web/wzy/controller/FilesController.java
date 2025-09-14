package cn.com.web.wzy.controller;

import cn.com.web.wzy.entity.ResponseBodyEntity;
import cn.com.web.wzy.service.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FilesController extends BaseController{

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    /**
     * 查询文件
     * @return 文件列表
     */
    @Operation(summary = "获取文件夹和文件列表")
    @GetMapping
    public ResponseBodyEntity<List<Map<String, String>>> queryFiles(@RequestParam String path) {
        return ResponseBodyEntity.ok(filesService.queryFiles(path));
    }

    /**
     * 删除文件和文件夹
     * @param params 参数
     * @return 删除情况
     */
    @Operation(hidden = true)
    @DeleteMapping("/delete")
    public ResponseBodyEntity<Boolean> delete(@RequestBody Map<String, String> params) {
        return ResponseBodyEntity.ok(filesService.deleteFile(params));
    }
}
