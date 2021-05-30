package com.kill.provider.mapper;

import com.kill.api.model.Killact;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KillDAO {

    String TABLE_NAME = " killact ";

    String INSERT_NAME = " startTime, endTime, name, descri, userId, daycount, discount";

    String SELECT_NAME = " id, " + INSERT_NAME;

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " order by endTime desc"})
    List<Killact> selectAll();

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where id = #{id}"})
    Killact selectById(int id);

    @Insert({"insert into ", TABLE_NAME, " ( ", INSERT_NAME, " ) values (#{startTime}, #{endTime},#{name},#{descri},#{userId},#{daycount},#{discount})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addAct(Killact killact);

}
