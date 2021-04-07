package com.kill.provider.mapper;

import com.kill.api.model.Profile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ProfileDAO {

    String TABLE_NAME = " profile ";
    String INSERT_FIELDS = " bio, sex, name, nickName, head_url, location, birthDay, userId, telNum, cardID, job, status, createDate ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Select({"select count(*) from ", TABLE_NAME, "order by id desc"})
    int count();

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " order by id desc limit #{start}, #{end}"})
    List<Profile> selectAll(int start, int end);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where userId=#{userId}"})
    Profile selectByUserId(int userId);

    @Update({"update ", TABLE_NAME,
            " set bio=#{bio}, sex=#{sex}, name=#{name}, nickName=#{nickName}, location=#{location}, birthDay=#{birthDay}, telNum=#{telNum}, cardID=#{cardID}, job=#{job} where userId=#{userId}"})
    void updateProfile(String bio, String sex, String name, String nickName, String location, Date birthDay, String telNum, String cardID, String job, int userId);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{bio},#{sex},#{name},#{nickName}, #{head_url},#{location},#{birthDay},#{userId},#{telNum},#{cardID},#{job}, 0,#{createDate})"})
    void addProfile(Profile p);

    @Update({"update ", TABLE_NAME, " set head_url=#{head_url} where userId=#{userId}"})
    void updateHead_url(String head_url, int userId);

    @Update({"update ", TABLE_NAME, " set status = 1 where userId = #{userId}"})
    void updateStatus(int userId);

    @Update({"update ", TABLE_NAME, " set status = 0 where userId = #{userId}"})
    void cancelStatus(int userId);

}
