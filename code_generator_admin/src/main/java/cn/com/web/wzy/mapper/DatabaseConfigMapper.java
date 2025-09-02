package cn.com.web.wzy.mapper;

import cn.com.web.wzy.dto.DatabaseConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * database_config数据表Mapper
 */
@Mapper
public interface DatabaseConfigMapper {
    List<DatabaseConfig> queryDatabaseConfig();

    DatabaseConfig queryDatabaseConfigById(Integer id);

    Boolean updateDatabaseConfig(DatabaseConfig databaseConfig);

    Boolean deleteDatabaseConfigById(Integer id);
}
