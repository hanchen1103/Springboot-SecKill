package com.kill.consumer.controller;

import com.kill.api.model.Profile;
import com.kill.api.model.User;
import com.kill.consumer.service.impl.LoginServiceImpl;
import com.kill.consumer.service.impl.ProfileServiceImpl;
import com.kill.consumer.service.impl.UserServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginServiceImpl loginService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ProfileServiceImpl profileService;

    /**
     * 登录功能 验证是否存在username，将用户传来的password加上自身的salt通过md5加密，验证与user表中的password字段是否一样
     * 如果都通过验证，生成ticket（包含在userservice.register返回的map中）如果存在ticket键，则生成cookie，
     * 使用HttpServletResponse response设置进客户端，为用户生成基础信息，插入user和profile表中
     * @param jsonMap map结构，包含字段username代表账户，password代表密码
     * @param response 设置cookie，可以让浏览器鉴别用户，同时设置为已登录的状态
     * @return code为0代表注册成功，msg为用户在user表和profile表中的信息，如果为其他则表示错误
     */
    @PostMapping(value = "login", produces = {"application/json;charset=UTF-8"})
    public String login(@RequestBody Map<String, String> jsonMap,
                        HttpServletResponse response) {
        String username = jsonMap.get("username");
        String password = jsonMap.get("password");
        try {
            Map<String, Object> map = loginService.Login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                Map<String, Object> res = new HashMap<>();
                User user = userService.selectByName(username);
                Profile pro = profileService.selectByUserId(user.getId());
                res.put("user", user);
                res.put("pro", pro);
                return jsonUtil.getJSONString(0, res);
            }
        } catch (IllegalAccessException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return jsonUtil.getJSONString(500);
    }

    /**
     * 退出，通过修改表loginticket中的status为1代表用户退出
     * @param ticket cookie值，如果修改则表示不可用
     * @return json形式的字符串，code为0代表退出成功
     */
    @PostMapping("logout")
    public String logout(@CookieValue("ticket") String ticket) {
        loginService.layout(ticket);
        return jsonUtil.getJSONString(0);
    }

    /**
     * 注册功能， 为新用户随机生成四个字符的salt，然后进行md5加密，作为入库的密码，接着生成ticket（包含在userservice.register返回的map中）
     * 如果存在ticket键，则生成cookie，使用HttpServletResponse response设置进客户端，为用户生成基础信息，插入user和profile表中
     * @param u map结构，包含字段username代表账户，password代表密码
     * @param response 设置cookie，可以让浏览器鉴别用户，同时设置为已登录的状态
     * @return code为0代表注册成功，如果为其他则表示错误
     */
    @PostMapping(value = "reg", produces = "application/json;charset=UTF-8")
    public String reg(@RequestBody Map<String, String> u,
                      HttpServletResponse response) {
        try {
            String username = u.get("username");
            String password = u.get("password");
            Map<String, Object> map = loginService.register(username, password);
            if(map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                Profile po = new Profile();
                po.setUserId(Integer.parseInt(String.valueOf(map.get("id"))));
                po.setSex("man");
                po.setCreateDate(new Date());
                profileService.addProfile(po);
                //backService.addPic(usersevice.selectByName(username).getId(), "http://139.196.58.222:8080/demo/image/back1.jpg");
                return jsonUtil.getJSONString(0);
            } else {
                return jsonUtil.getJSONString(1, map.get("msg"));
            }
        }catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return jsonUtil.getJSONString(1);
        }
    }
}
