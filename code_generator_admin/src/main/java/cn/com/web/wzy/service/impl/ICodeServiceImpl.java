package cn.com.web.wzy.service.impl;

import cn.com.web.wzy.service.CodeService;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ICodeServiceImpl implements CodeService {

    @Override
    public List<String> filenames(RequestVo requestVo) {
        return List.of();
    }

    @Override
    public Map<String, String> content(RequestVo requestVo) {
        return Map.of();
    }

    @Override
    public Map<String, String> download(RequestVo requestVo) {
        return Map.of();
    }
}
