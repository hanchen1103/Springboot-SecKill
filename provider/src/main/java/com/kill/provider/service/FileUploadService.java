package com.kill.provider.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {

    public String upload(HttpServletRequest request) {
        List<MultipartFile> files = new ArrayList<>();
        int count = Integer.parseInt(request.getParameter("count"));
        for(int i = 0; i <= count; i ++) {
            MultipartFile t = ((MultipartHttpServletRequest) request).getFiles("file" + i).get(0);
            files.add(t);
        }
        String filePath = "/usr/img/";
        StringBuilder content = new StringBuilder();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return "upload error!";
            }
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            content.append("http://47.101.154.44/demo/image/").append(fileName).append("|");
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                return "upload error!";
            }
        }
        return content.toString();
    }
}
