package com.kill.provider.mapper;

import com.kill.api.model.ShopCar;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface ShopCarDAO {
    String TABLE_NAME = " ShopCar ";
    String INSERT_NAME = " size, stockId, userId";
    String SELECT_NAME = " id, " + INSERT_NAME;

    @Select({"select", SELECT_NAME, " from ", TABLE_NAME, " where id = #{id}"})
    ShopCar selectById(int id);

    @Insert({"insert into ", TABLE_NAME, " ( ", INSERT_NAME, " ) values (#{size},#{stockId},#{userId})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addCar(ShopCar shopCar);

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where userId=#{userId} limit #{start},#{offset}"})
    List<ShopCar> selectByUserId(int userId, int start, int offset);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    int deleteShopCar(int id);

    @Select({"select count(id) from ", TABLE_NAME, " where userId = #{userId}"})
    Integer getCount(int userId);
}
