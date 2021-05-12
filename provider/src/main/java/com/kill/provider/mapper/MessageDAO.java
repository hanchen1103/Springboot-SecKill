package com.kill.provider.mapper;

import com.kill.api.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " fromId, toId, content, CreateDate, hasRead, messageId ";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(" , INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{CreateDate},#{hasRead},#{messageId})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            "where messageId=#{messageId} order by id desc limit #{offset}, #{limit}"})
    List<Message> selectByMessageId(@Param("messageId") String messageId,
                                    @Param("offset") int offset,
                                    @Param("limit") int limit);


    @Select({"select m.* from message as m right join (select max(id) as max_id from message where fromId = #{userId} or toId=#{userId} group by messageId) as a on m.id = a.max_id order by CreateDate desc limit 0, 50;"})
    List<Message> getMessageProfile(@Param("userId") int userId,
                                    @Param("offset") int offset,
                                    @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where hasRead=0 and toId=#{userId} and messageId=#{messageId}"})
    int getMessgaeCount(@Param("userId") int userId,
                        @Param("messageId") String messageId);

    @Update({"update message set hasRead=0 where id in (select min_id from (select id as min_id from message where toId=#{toId} and fromId=#{fromId} order by id desc limit 0, 1) as a);" })
    int setRead(int toId, int fromId);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where toId=#{toId} and fromId=#{fromId} order by id desc limit 0, 1"})
    Message selectLatest(int toId, int fromId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where messageId=#{messageId} order by id desc limit 0, 1"})
    Message selectLatestById(String messageId);
}
