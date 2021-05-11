package com.kill.provider.mapper;

import com.kill.api.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentDAO {

    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " userId, createDate, entityId, entityType, status, content ";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId}, #{createDate}, #{entityId}, #{entityType}, #{status}, #{content})"})
    int addcomment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, "where id=#{id}"})
    Comment selectById(int id);

    @Select({"select count(id) from ",
            TABLE_NAME, "where entityId=#{entityId} and entityType=#{entityType} and status = 0"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where entityId=#{entityId} and entityType=#{entityType} and status = 0 order by createDate desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
                                        @Param("entityType") int entityType);

}
