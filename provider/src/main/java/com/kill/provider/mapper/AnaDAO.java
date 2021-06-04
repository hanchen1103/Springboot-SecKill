package com.kill.provider.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AnaDAO {

    @Select({"select DATE_FORMAT(createDate,'%Y-%m-%d') as createDate, TO_DAYS(createDate) as t, sum(money) as money from amount as a group by t;"})
    List<Map<String, Object>> selectDateMoney();

    @Select({"SELECT count(*) as cnt FROM LikeRec where DATE_FORMAT( createDate, '%Y%m') = DATE_FORMAT( CURDATE( ),'%Y%m')"})
    int getLike();

    @Select({"SELECT count(*) as cnt FROM `profile` where DATE_FORMAT( createDate, '%Y%m') = DATE_FORMAT( CURDATE( ) ,'%Y%m')"})
    int getNewProfile();

    @Select({"select count(a.id) as cnt from (select * from loginticket where TO_DAYS(expired) - 1 = TO_DAYS(NOW()) and status = 0 group by userId) as a;"})
    int selectLoginNow();

    @Select({"select sum(money) as m from amount where TO_DAYS(createDate) = TO_DAYS(now());"})
    Object selectAmountDaily();

    @Select({"select sum(money) as m from amount where TO_DAYS(NOW( )) - TO_DAYS(createDate) = 1;"})
    Object selectAmountYesterday();

    @Select({"select sum(money) as m from amount where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(createDate);"})
    Object selectAmountMonth();

    @Select({"SELECT sum(money) as m FROM amount WHERE PERIOD_DIFF( date_format(now(),'%Y%m'),date_format(createDate,'%Y%m')) =1;"})
    Object selectAmountSeason();

    @Select({"select sum(money) as m from amount where YEAR(createDate)=YEAR(NOW());"})
    Object selectAmountYear();


    @Select({"select count(*) as cnt from loginticket where TO_DAYS(expired) - 1 = TO_DAYS(now());"})
    int selectNowDays();

    @Select({"select count(*) as cnt from loginticket where TO_DAYS(expired) = TO_DAYS(now());"})
    int selectYestDays();

    @Select({"select count(*) as cnt from loginticket where DATE_FORMAT(expired, '%Y%m') = DATE_FORMAT(CURDATE( ), '%Y%m');"})
    int selectMonth();

    @Select({"select count(*) as cnt from loginticket where PERIOD_DIFF( date_format(now( ), '%Y%m') , date_format(expired, '%Y%m')) = 1;"})
    int selectLastMonth();

    @Select({"select count(userId) as num, a.expired from (select userId, expired from loginticket order by expired) as a group by expired order by a.expired desc"})
    List<Map<String, Object>> daily();

    @Select({"SELECT count(id) as num, location FROM profile group by location;"})
    List<Map<String, Object>> profileLocation();

    @Select({"SELECT count(id) as num, location FROM member group by location;"})
    List<Map<String, Object>> memberLocation();

    @Select({"select a.name, a.pointx, a.pointy from location as a"})
    List<Map<String, Object>> viewLocation();

    @Select({"SELECT count(id) as cnt, question FROM `history` where question != '' group by question order by cnt desc \n" +
            "limit 0, 100"})
    List<Map<String, Object>> HotSearch();

    @Select({"select a.userId, a.longitude, a.latitude from CurLoc as a"})
    List<Map<String, Object>> CurLoc();
}
