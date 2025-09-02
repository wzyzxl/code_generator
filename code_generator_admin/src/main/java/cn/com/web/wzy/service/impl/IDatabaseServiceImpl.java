package cn.com.web.wzy.service.impl;

import cn.com.web.wzy.service.DatabaseService;
import cn.com.web.wzy.vo.RequestVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IDatabaseServiceImpl implements DatabaseService {
    @Override
    public List<Map<String, String>> types() {
        return List.of();
    }

    @Override
    public Map<String, Object> connections(RequestVo requestVo) {
        return Map.of();
    }

    @Override
    public List<String> patterns(RequestVo requestVo) {
        return List.of();
    }

    @Override
    public List<String> tables(RequestVo requestVo) {
        return List.of();
    }

    @Override
    public Map<String, Object> save(RequestVo requestVo) {
        return Map.of();
    }
}
