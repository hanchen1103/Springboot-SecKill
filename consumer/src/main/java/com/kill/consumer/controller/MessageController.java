package com.kill.consumer.controller;


import com.kill.api.model.FileType;
import com.kill.api.model.Message;
import com.kill.api.model.Stock;
import com.kill.api.model.User;
import com.kill.consumer.service.impl.*;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@ComponentScan(value = "com.kill.consumer.config")
@RestController
@RequestMapping("/message")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MessageServiceImpl messageService;

    @Autowired
    ProfileServiceImpl profileService;

    @Autowired
    StockServiceImpl stockService;

    @Autowired
    UploadFileServiceImpl uploadFileService;

    /**
     * 发送信息，首先检验用户是否登录，如果登录成功，则获取所有字段，将文件类型的首先重命名并记录，同时放入服务器的/usr/img
     * 路径下，接着新建消息对象，将字段都填入，fromId填写本用户的Id,toId填写发送对方的id，包括内容的路径或者文字，
     * 设置此条消息为未读（hasRead）,如果上一条两者之间的消息不为0,则未读消息在以上的基础上+1，将此对象插入数据库
     * @param request 包含toId(发送对方的id),type(发送消息的类型,有文字，视频，音频，图片),content（消息内容）
     * @return json形式的字符串 code为1代表未登录，code为999代表文件上传失败，code为0代表发送成功
     */
    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public String addmessage(HttpServletRequest request) {
        StringBuilder content;
        try {
            int toId =  Integer.parseInt(request.getParameter("toId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            User user = userService.selectById(toId);
            int type = Integer.parseInt(request.getParameter("type"));
            if(type == FileType.text) {
                content = new StringBuilder(request.getParameter("content"));
            } else {
                MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
                content = new StringBuilder(uploadFileService.uploadMessageFile(multipartFile));
            }
            Message message = new Message();
            message.setCreateDate(new Date());
            message.setContent(content.toString());
            message.setFromId(userId);
            message.setToId(user.getId());
            if(messageService.selectLatest(toId, userId) == null) {
                message.setHasRead(1);
                messageService.addMessage(message);
                return jsonUtil.getJSONString(0);
            }
            message.setHasRead(messageService.selectLatest(toId, userId).getHasRead() + 1);
            messageService.addMessage(message);
            return jsonUtil.getJSONString(0);
        } catch(Exception e) {
            logger.error(e.getMessage());
            return jsonUtil.getJSONString(500, "发送失败");
        }
    }

    /**
     * 获取个人用户的消息列表
     * 通过本地线程池得到自身的用户id，在message中找到50条关于用户自身的对话（包括fromId, toId）
     * 获取最新的消息记录,于此每一条附带创建时间(_createdate)，相关的用户(user)，昵称（nickName），未读数量（isRead）
     * @return json形式的消息总览列表
     */
    @GetMapping(value = "/",produces = {"application/json;charset=UTF-8"})
    public String messageList(int userId) {
        try {
            List<Message> messageList = messageService.getMessageList(userId, 0, 50);
            List<Map<String, Object>> messages = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            for(Message message : messageList) {
                Map<String, Object> m = new HashMap<>();
                m.put("mess", message);
                Date date = message.getCreateDate();
                String newdate = sdf.format(date);
                m.put("_cretedate", newdate);
                int targetId = message.getFromId() == userId ? message.getToId() : message.getFromId();
                m.put("user", userService.selectById(targetId));
                m.put("nickName", profileService.selectByUserId(targetId).getNickName());
                int isRead = message.getFromId() == userId ? 0 : message.getHasRead();
                m.put("isRead", isRead);
                messages.add(m);
            }
            return jsonUtil.getJSONString(0, messages);
        } catch (IllegalAccessException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return jsonUtil.getJSONString(500);
    }

    /**
     * 获取消息详情页，由解析messageId解析得到fromId和toId(messageId由fromId和toId + '-'组成),messageService规定
     * 更小的id在前面，可以获取自身的id,判断自身为from还是to,如果是from那么不清空红点(未读),反之设红点为0,
     * 通过messageId获取所有相关对话，并且可以获得相对的用户的Id,获取用户详情，对所有消息进行处理，添加type，
     * 通过后缀的'.'来获取type,将每一条消息的类型和内容放在map中，加入list队列，和自身用户信息，对话用户信息一起放入
     * Map中返回。
     * @param jsonMap 包含字段 messageId(指定消息id)，start(读取起点)，end(相对于起点的偏移量)
     * @return map中包含message消息列表(allMessage)，自身用户信息(fromUser)，对话用户信息(toUser)，消息列表中包含消息内容和消息类型
     */
    @PostMapping(value = "/chat", produces = "application/json;charset=UTF-8")
    public String getchat(@RequestBody Map<String, String> jsonMap) {
        Map<String, Object> map = new HashMap<>();
        String messageId = jsonMap.get("messageId");
        int start = Integer.parseInt(jsonMap.get("start"));
        int end = Integer.parseInt(jsonMap.get("end"));
        int userId = Integer.parseInt(jsonMap.get("userId"));
        String[] s = messageId.split("-");
        int otherId;
        if(s[0].equals(String.valueOf(userId))) otherId = Integer.parseInt(s[1]);
        else otherId = Integer.parseInt(s[0]);
        Message mes = messageService.selectByMessageId(messageId);
        try {
            if(userId == mes.getToId())
                messageService.isRead(userId, otherId);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            return jsonUtil.getJSONString(500);
        }
        map.put("fromUser", userService.selectById(userId));
        map.put("toUser", userService.selectById(otherId));
        try {
            map.put("myname", profileService.selectByUserId(userId).getNickName());
            List<Message> mgl = messageService.getMessageById(messageId, start, end);
            List<Map<String, Object>> allMessage = new ArrayList<>();
            for(Message ml : mgl) {
                Map<String, Object> m = new HashMap<>();
                m.put("message", ml);
                String type = "";
                String con = ml.getContent();
                for (int i = con.length() - 1; i >= 0; i --) {
                    if(con.charAt(i) == '.') {
                        type = con.substring(i + 1);
                        break;
                    }
                }
                m.put("type", type);
                allMessage.add(m);
            }

            map.put("otherProfile", profileService.selectByUserId(otherId));
            map.put("chatMessage", allMessage);
        } catch(Exception e) {
            logger.error("error" + e.getMessage());
            return jsonUtil.getJSONString(1);
        }
        return jsonUtil.getJSONString(0, map);
    }

    /**
     * 投诉
     * @param map stockId：投诉商品id，content:id+投诉内容 9999099代表系统（管理员）
     * @return json code=0
     */
    @PostMapping(value = "/complaint", produces = "application/json;charset=UTF-8")
    public String comp(@RequestBody Map<String, String> map) {
        int userId = Integer.parseInt(map.get("userId"));
        Message message = new Message();
        message.setToId(9999099);
        String content;
        String stockId = map.get("stockId");
        content = stockId + map.get("content");
        message.setContent(content);
        message.setFromId(Integer.parseInt(map.get("userId")));
        message.setCreateDate(new Date());
        if(messageService.selectLatest(9999099, userId) == null) {
            message.setHasRead(1);
            messageService.addMessage(message);
            return jsonUtil.getJSONString(0);
        }
        message.setHasRead(0);
        messageService.addMessage(message);
        return jsonUtil.getJSONString(0);
    }

    /**
     * 获取评论
     * @param start limit
     * @param end offset
     * @return res:投诉者信息和投诉内容
     */
    @GetMapping(value = "/complaint",  produces = "application/json;charset=UTF-8")
    public String readComp(Integer start, Integer end) {
        try {
            List<Message> list = messageService.readComplaint(start, end);
            List<Map<String, Object>> res = new LinkedList<>();
            for(Message i : list) {
                Map<String, Object> t = new HashMap<>();
                t.put("profile", profileService.selectByUserId(i.getFromId()));
                t.put("comp", i);
                res.add(t);
            }
            return jsonUtil.getJSONString(0, res);
        } catch (IllegalAccessException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return jsonUtil.getJSONString(500);
    }

    /**
     * 处理投诉
     * @param msgId 获取投诉信息
     * @return json code=0
     */
    @PutMapping(value = "/complaint", produces = "application/json;charset=UTF-8")
    public String setComp(int msgId) {
        Message msg = messageService.selectById(msgId);
        String content = msg.getContent();
        int stockId = content.charAt(0) - '0';
        Stock stock = stockService.getStockById(stockId);
        stock.setStatus(999);
        stockService.updateStockById(stock);
        messageService.updateRead(msgId);
        return jsonUtil.getJSONString(0);
    }
}
