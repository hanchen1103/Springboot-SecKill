package com.kill.consumer.controller;

import com.kill.api.model.EntityType;
import com.kill.api.model.Profile;
import com.kill.api.model.User;
import com.kill.consumer.service.impl.LikeServiceImpl;
import com.kill.consumer.service.impl.ProfileServiceImpl;
import com.kill.consumer.service.impl.UploadFileServiceImpl;
import com.kill.consumer.service.impl.UserServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    UserServiceImpl userSevice;

    @Autowired
    ProfileServiceImpl profileService;

    @Autowired
    LikeServiceImpl followService;

    @Autowired
    UploadFileServiceImpl uploadFileService;

    /**
     * 获取所有用户信息列表,分页加载,分页数量由前端规定，通过selectAll加载所需要的用户详情数量
     * @param map map中包换current和pageSize字段，分别代表为当前页面和此页需要加载的容量
     * @return 返回json包装,code为0的用户详情列表
     */
    @PostMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public String all(@RequestBody Map<String, String> map) {
        try {
            int start = Integer.parseInt(map.get("current"));
            int end = Integer.parseInt(map.get("pageSize"));
            Map<String, Object> res = new HashMap<>();
            res.put("count", profileService.count());
            start -= 1;
            res.put("list", profileService.selectAll(start * end, end));
            return jsonUtil.getJSONString(0, res);
        } catch (NullPointerException | NumberFormatException | IllegalAccessException e) {
            logger.error(e.getMessage());
            return jsonUtil.getJSONString(500);
        }
    }


    /**
     * 查看profile详情
     * @param map userId为用户id，otherId代表查看对方id
     * @return json形式的字符串，map中包含自己的用户详情，对方的用户详情
     */
    @PostMapping(value = "/manager", produces = "application/json;charset=UTF-8")
    public String getManager(@RequestBody Map<String, String> map) {
        try {
            int userId = Integer.parseInt(map.get("userId"));
            int otherId = Integer.parseInt(map.get("otherId"));
            Profile pro = profileService.selectByUserId(userId);
            User u = userSevice.selectById(userId);
            Map<String, Object> res = new HashMap<>();
            res.put("ownProfile", pro);
            res.put("otherProfile", profileService.selectByUserId(otherId));
            res.put("user", u);
            res.put("otherUser", userSevice.selectById(otherId));
            return jsonUtil.getJSONString(0, res);
        } catch (NullPointerException | NumberFormatException e) {
            logger.error(e.getMessage());
            return jsonUtil.getJSONString(500);
        }

    }

    /**
     *访问指定id的用户列表界面，包含是否关注，粉丝数量，作品列表,需要当前用户登录才可执行
     * @param userId 为访问界面的用户id，Integer类型
     * @return json包装的Map<String, Object>类型，其中包含是否关注(flag)，当前用户(curuser)，访问的用户详情页(profile)
     * 访问用户的粉丝数(fanscount)，访问用户的关注数(followeecount)，访问用户的背景图片(back)，
     * 访问用户的段位积分(rank)，访问用户的作品列表(vl).如果当前用户没有登录则会返回code为1,msg为user missing的错误提示
     */
    @GetMapping(value = "/{userId}/{myId}", produces = "application/json;charset=UTF-8")
    public String getothersProfile(@PathVariable(value = "userId", required = false) Integer userId,
                                   @PathVariable(value = "myId", required = false) Integer myId) {
        Map<String, Object> map = new HashMap<>();
        Profile pro = profileService.selectByUserId(userId);
        map.put("flag", followService.getLikeStatus(myId, EntityType.ENTITYTYPE_USER, userId));
        map.put("followeecount", followService.getLikeCount(userId, EntityType.ENTITYTYPE_USER));
        map.put("curuser", userSevice.selectById(userId));
        map.put("profile", pro);

        List<Map<String, Object>> vl = new ArrayList<>();
        return jsonUtil.getJSONString(0, map);
    }

    /**
     * 更新用户信息，如果缺少字段，则赋为控制
     * @param map 包含字段bio(个性签名),sex(性别),nickName(昵称),name(姓名),location(地址),
     *            telNum(电话号码),cardId(身份证号码),job(工作),birthDay(出生日期)
     * @return code为0的状态码，用json字符串包装
     * @throws ParseException 日期转换
     */
    @PutMapping(value = "/",  produces = "application/json;charset=UTF-8")
    public String updateProfile(@RequestBody Map<String, Object> map) throws ParseException {
        String bio = (String) map.getOrDefault("bio", " ");
        String sex = (String) map.getOrDefault("sex", " ");
        String nickName = (String) map.getOrDefault("nickName", " ");
        String name = (String) map.getOrDefault("name", " ");
        String location = (String) map.getOrDefault("location", " ");
        String telNum = (String) map.getOrDefault("telNum", " ");
        String cardID = (String) map.getOrDefault("cardID", " ");
        String job = (String) map.getOrDefault("job", " ");
        String birth = (String) map.getOrDefault("birthDay", " ");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDay = sdf.parse(birth);
            profileService.updateProfile(bio, sex, name, nickName, location, birthDay, telNum, cardID, job, Integer.parseInt((String) map.get("userId")));
        } catch (ParseException e) {
            logger.error(e.getMessage());
            profileService.updateProfile(bio, sex, name, nickName, location, null, telNum, cardID, job, Integer.parseInt((String) map.get("userId")));
        }
        return jsonUtil.getJSONString(0);
    }

    /**
     * 更新用户信息，如果缺少字段，则赋为控制
     * @param map 包含字段bio(个性签名),sex(性别),nickName(昵称),name(姓名),location(地址),
     *            telNum(电话号码),cardId(身份证号码),job(工作),birthDay(出生日期)
     * @return code为0的状态码，用json字符串包装
     * @throws ParseException 日期转换
     */
    @PutMapping(value = "/web",  produces = "application/json;charset=UTF-8")
    public String updateProfileWeb(@RequestBody Map<String, Object> map) throws ParseException {
        String bio = (String) map.getOrDefault("bio", " ");
        String sex = (String) map.getOrDefault("sex", " ");
        String nickName = (String) map.getOrDefault("nickName", " ");
        String name = (String) map.getOrDefault("name", " ");
        String location = (String) map.getOrDefault("location", " ");
        String telNum = (String) map.getOrDefault("telNum", " ");
        String cardID = (String) map.getOrDefault("cardID", " ");
        String job = (String) map.getOrDefault("job", " ");
        String birth = (String) map.getOrDefault("birthDay", " ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int userId = Integer.parseInt((String) map.get("userId"));
        Date birthDay = sdf.parse(birth);
        profileService.updateProfile(bio, sex, name, nickName, location, birthDay, telNum, cardID, job, userId);
        return jsonUtil.getJSONString(0);
    }

    /**
     * 更换头像,将新的头像图片重命名,放入服务器对应的路径中，更新数据库中user表和profile中的head_url字段
     * @param request request包含文件流(图片)
     * @return 状态码0为成功，其他则为失败，用json包装
     */
    @PostMapping(value = "/head_url",  produces = "application/json;charset=UTF-8")
    public String updateHeadurl(HttpServletRequest request) {
        try {
            MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            return jsonUtil.getJSONString(200, uploadFileService.uploadHeadUrl(multipartFile, userId));
        } catch (NullPointerException | NumberFormatException | IllegalAccessException | IOException e) {
            logger.error(e.getMessage());
            return jsonUtil.getJSONString(500, "upload avatar failed");
        }
    }
}
