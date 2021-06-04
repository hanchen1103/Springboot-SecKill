package com.kill.provider.mapper;

import com.kill.api.model.history;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HistoryDAO {
    String TABLE_NAME = " history ";
    String INSERT_NAME = " CreateDate, question, userId ";
    String SELECT_NAME = "id, " + INSERT_NAME;

    @Select({"select ", SELECT_NAME, " from ", TABLE_NAME, " where userId=#{userId} group by question order by CreateDate desc limit 0, 10"})
    List<history> selectAll(int userId);

    @Insert({"insert into", TABLE_NAME, " ( ", INSERT_NAME, " ) values (#{CreateDate},#{question},#{userId})"})
    int addHistory(history h);
}
