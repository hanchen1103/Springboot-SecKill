package com.kill.consumer.controller;


import com.crossoverjie.distributed.annotation.CommonLimit;
import com.kill.api.model.Stock;
import com.kill.consumer.service.impl.StockServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@ComponentScan(value = "com.kill.consumer.config")
@ComponentScan(value = "com.crossoverjie.distributed.intercept")
@RestController
@RequestMapping("/stock")
public class StockController {

    private final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockServiceImpl stockService;

    @Value("${my.url}")
    private String url;

    @Value("${my.port}")
    private String port;

    /**
     * 获取商品库存信息
     * @param id 商品库存id
     * @return 商品信息
     */
    @CommonLimit
    @GetMapping(path = "/{id}", produces = {"application/json;charset=UTF-8"})
    public String getStock(@PathVariable("id") int id) {
        return jsonUtil.getJSONString(0, stockService.getStockById(id));
    }

    /**
     * 上传商品信息
     * @param request 包含count:表示数量，descri:商品描述，tag:标签, name:商品名称
     * @return 上传状态码
     */
    @PostMapping(value = "/",produces = {"application/json;charset=UTF-8"})
    public String addStock(HttpServletRequest request) {
        List<MultipartFile> files = new ArrayList<>();
        StringBuilder content = new StringBuilder();
        try {
            int count = Integer.parseInt(request.getParameter("cnt"));
            for (int i = 1; i <= count; i++) {
                MultipartFile t = ((MultipartHttpServletRequest) request).getFiles("file" + i).get(0);
                files.add(t);
            }
            //String filePath = "D://linux//";
            String filePath = "/usr/img/";
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (file.isEmpty()) {
                    return jsonUtil.getJSONString(999, "上传第" + i + "个文件失败");
                }
                String fileName = file.getOriginalFilename();
                assert fileName != null;
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID() + suffixName;
                content.append("http://").append(url).append(":").append(port).append("/consumer-0.0.1-SNAPSHOT/image/").append(fileName).append("|");
                File dest = new File(filePath + fileName);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                    return jsonUtil.getJSONString(999, "上传失败");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return jsonUtil.getJSONString(1, "上传失败");
        }

        Stock stock = new Stock();
        stock.setCount(Integer.parseInt(request.getParameter("count")));
        stock.setCreateDate(new Date());
        stock.setDescri(request.getParameter("descri"));
        stock.setPic(content.toString());
        stock.setUserId(Integer.parseInt(request.getParameter("userId")));
        stock.setName(request.getParameter("name"));
        stock.setTag(request.getParameter("tag"));
        stock.setPrice(new BigDecimal(request.getParameter("price")));
        return jsonUtil.getJSONString(0, stockService.addStock(stock));
    }
}
