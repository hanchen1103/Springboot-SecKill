package com.kill.provider.mapper;


import com.kill.api.model.power;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PowerDAO {

    String TABLE_NAME = " power ";

    String INSERT_NAME = " voltage, electric_current, temperature, humidity, flow, userId, createDate";

    String SELECT_NAME = " id, " + INSERT_NAME;

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where id = #{id}"})
    power selectById(int id);

    @Insert({"insert into ", TABLE_NAME, " ( ", INSERT_NAME,
            " ) values (#{voltage},#{electric_current},#{temperature},#{humidity},#{flow},#{userId},#{createDate})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addPower(power w);

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where userId = #{userId} and " +
            " PERIOD_DIFF( date_format(now(),'%Y%m'),date_format(createDate,'%Y%m')) = 0 " +
            "order by createDate desc"})
    List<power> selectByUserIdSeason(int userId);

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where userId = #{userId} and " +
            "DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(createDate) order by createDate desc"})
    List<power> selectByUserIdMonth(int userId);

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where userId = #{userId} " +
            "and YEAR(createDate)=YEAR(NOW()) order by createDate desc"})
    List<power> selectByUserIdYear(int userId);
}
