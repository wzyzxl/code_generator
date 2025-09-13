package cn.com.web.wzy.controller;

import cn.com.web.wzy.entity.ResponseBodyEntity;
import cn.com.web.wzy.service.CodeService;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 代码类请求
 */
@RestController()
@RequestMapping("/code")
public class CodeController extends BaseController {

    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    /**
     * 获取文件名
     *
     * @param requestVo 请求
     * @return 文件名
     */
    @PostMapping("/filenames")
    public ResponseBodyEntity<List<String>> filenames(@RequestBody RequestVo requestVo) {
        return ResponseBodyEntity.ok(codeService.filenames(requestVo));
    }

    /**
     * 获取代码内容
     *
     * @param requestVo 请求
     * @return 代码
     */
    @PostMapping("/content")
    public ResponseBodyEntity<Map<String, String>> content(@RequestBody RequestVo requestVo) {
        return ResponseBodyEntity.ok(codeService.content(requestVo));
    }

    /**
     * 下载代码
     *
     * @param requestVo 请求
     * @return 下载链接
     */
    @PostMapping("/download")
    public ResponseBodyEntity<Map<String, String>> download(@RequestBody RequestVo requestVo) {
        return ResponseBodyEntity.ok(codeService.download(requestVo));
    }
}
