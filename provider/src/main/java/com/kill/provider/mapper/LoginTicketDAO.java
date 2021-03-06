package com.kill.provider.mapper;

import com.kill.api.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginTicketDAO {
    String TABLE_NAME = " loginticket ";
    String INSERT_FIELDS = " userId, expired, status, ticket ";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId}, #{expired}, #{status}, #{ticket})"})
    int addticket(LoginTicket ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, "where ticket=#{ticket}" })
    LoginTicket selctByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
