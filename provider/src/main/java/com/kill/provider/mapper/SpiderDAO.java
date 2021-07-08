package com.kill.provider.mapper;

import com.kill.api.model.spiderProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpiderDAO {
    String TABLE_NAME = " python_spider ";
    String INSERT_NAME = " band, info, url, price, pic, name";
    String SELECT_NAME = " id, " + INSERT_NAME;

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where name = #{name}"})
    List<spiderProduct> selectByname(String name);

    @Delete({"truncate table python_spider"})
    int deletetable();

}
