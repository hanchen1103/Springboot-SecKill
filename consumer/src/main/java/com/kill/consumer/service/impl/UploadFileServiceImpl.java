package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.service.FileService;
import com.kill.api.service.ProfileService;
import com.kill.api.service.UserService;
import com.kill.consumer.service.UploadFileService;
import com.kill.consumer.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystemException;

@Service
@org.springframework.stereotype.Service
public class UploadFileServiceImpl implements UploadFileService {

    @Reference
    FileService fileService;

    @Reference
    UserService userService;

    @Reference
    ProfileService profileService;

    @Value("${file.path-centos}")
    String filePath;

    @Value("${file.url-web}")
    String fileUrl;

    @Value("${file.port}")
    String filePort;

    @Value("${file.suffix-centos}")
    String fileSuffix;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadHeadUrl(MultipartFile multipartFile, Integer userId) throws IOException, IllegalAccessException {
        if(!FileUtil.isPic(fileService.getSuffixName(multipartFile))) {
            throw new FileSystemException("not correct type");
        }
        String fileName = fileService.uploadFile(multipartFile, filePath);
        String dbValue = "http://" + fileUrl + ":" + filePort + fileSuffix + fileName;
        userService.updateHead_url(dbValue, userId);
        profileService.updateHead_url(dbValue, userId);
        return dbValue;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadMessageFile(MultipartFile multipartFile) throws IOException {
        String fileName = fileService.uploadFile(multipartFile, filePath);
        String dbValue = "http://" + fileUrl + ":" + filePort + fileSuffix + fileName;
        return dbValue;
    }
}
