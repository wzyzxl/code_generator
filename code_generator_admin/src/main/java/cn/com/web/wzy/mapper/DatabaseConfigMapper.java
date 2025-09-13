package cn.com.web.wzy.mapper;

import cn.com.web.wzy.vo.RequestVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * database_config数据表Mapper
 */
@Mapper
public interface DatabaseConfigMapper {
    int queryByConfigName(RequestVo requestVo);

    boolean insertDatabaseConfig(RequestVo requestVo);
}
