package com.kill.provider.mapper;


import com.kill.api.model.Stock;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StockDAO {
    String TABLE_NAME = " stock ";
    String INSERT_NAME = " name, createDate, userId, tag, descri, pic, status, version, sale, count, price";
    String SELECT_NAME = " id, " + INSERT_NAME;

    /**
     * 根据库存商品id选择
     * @param id 库存商品id
     * @return Stock
     */
    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where id = #{id} and status = 0"})
    Stock selectById(int id);

    /**
     * 增加库存商品
     * @param stock 商品
     * @return stock.id
     */
    @Insert({"insert into ", TABLE_NAME, " ( ", INSERT_NAME, " ) values (#{name},#{createDate},#{userId},#{tag},#{descri},#{pic},#{status},#{version},#{sale},#{count},#{price})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addStock(Stock stock);

    /**
     * 根据库存商品userIdid选择
     * @param userId 商家id
     * @return Stock列表
     */
    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where userId=#{userId} and status = 0"})
    List<Stock> selectByUserId(int userId);

    /**
     * 根据库存商品id来更新状态码，达到删除的功能
     * @param id 库存商品id
     * @return 0或1
     */
    @Update({"update ", TABLE_NAME, " set status = 1 where id=#{id}"})
    int deleteStock(int id);


    /**
     * 更新商品库存信息
     * @return 0或1
     */
    @Update({"update", TABLE_NAME, " set name=#{name},tag=#{tag},descri=#{descri},pic=#{pic} where id =#{id}"})
    int updateStock(Stock stock);

    /**
     * 乐观锁更新商品库存信息
     * @return 0或1
     */
    @Update({"update", TABLE_NAME, " set version = version + 1, sale = sale + #{addSale} where id = #{id} and version=#{version}"})
    int updateStockByOptimisticLock(@Param("addSale") int addSale, @Param("id") int id, @Param("version") int version);

}
