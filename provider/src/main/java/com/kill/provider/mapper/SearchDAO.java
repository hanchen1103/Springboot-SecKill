package com.kill.provider.mapper;

import com.kill.api.model.Profile;
import com.kill.api.model.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchDAO {

    /**
     * 简易使用name, tag, descri做一个优先级搜索查询
     * @return List<stock>
     */
    @Select({"SELECT `stock`.*\n" +
            "            FROM `stock`, (SELECT `stock`.`id`, LOCATE(#{q}, concat(`name`, `tag`, `descri`)) `index`\n" +
            "                           from `stock`) `a`\n" +
            "            WHERE `a`.`index` > 0 AND `a`.`id`=`stock`.`id`\n" +
            "            order by `index` desc"})
    List<Stock> searchStock(String q);

    @Select({"select * from profile where nickName like '%${q}%' order by nickName"})
    List<Profile> selectProfile(String q);

}
