package com.kill.provider.mapper;

import com.kill.api.model.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@MapperScan
@Repository
public interface UseDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt, head_url, level, status";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Update({"update ", TABLE_NAME, " set level = #{level} where id=#{id}"})
    void updateLevel(int level, int id);

    @Update({"update ", TABLE_NAME, " set status = 1 where id=#{id}"})
    void updateUser(int id);

    @Update({"update ", TABLE_NAME, " set status = 0 where id=#{id}"})
    void cancel(int id);


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{salt},#{head_url},#{level},#{status})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, "where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, "where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

    @Update({"update ", TABLE_NAME, " set head_url=#{head_url} where id=#{id}"})
    void updateHead_url(String head_url, int id);



}
