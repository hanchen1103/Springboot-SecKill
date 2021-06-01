package com.kill.provider.mapper;

import com.kill.api.model.StockOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface StockOrderDAO {
    String TABLE_NAME = " stockOrder ";
    String INSERT_NAME = " stockId, userId, createDate, price, order_uuid";
    String SELECT_NAME = " id, " + INSERT_NAME;

    /**
     * 通过订单id获取订单详情
     * @param id 订单id
     * @return 订单
     */
    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where id = #{id}"})
    StockOrder selectById(int id);

    /**
     * 添加订单
     * @param stockOrder 订单
     * @return 0或1
     */
    @Insert({"insert into ", TABLE_NAME, " ( ", INSERT_NAME, " ) values (#{stockId},#{userId},#{createDate},#{price},#{order_uuid})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addStockOrder(StockOrder stockOrder);

    /**
     * 根据日期获取订单
     * @param createDate 日期
     * @param start 初始量
     * @param offset 偏移量
     * @return 订单列表
     */
    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " order by createDate desc limit #{start},{offset}"})
    List<StockOrder> selectOrderByDate(Date createDate, int start, int offset);

    /**
     * 根据用户Id获取订单
     * @param start 初始量
     * @param offset 偏移量
     * @return 订单列表
     */
    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, "where userId=#{userId} order by createDate desc limit #{start},#{offset}"})
    List<StockOrder> selectOrderUserId(int userId, int start, int offset);
}
