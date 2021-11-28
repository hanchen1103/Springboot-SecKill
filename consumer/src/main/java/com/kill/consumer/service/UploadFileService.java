package com.kill.consumer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;

public interface UploadFileService {

    /**
     * 上传头像
     * @return 是否成功
     */
    String uploadHeadUrl(MultipartFile multipartFile, Integer userId) throws IOException;

    /**
     * 上传聊天中的文件，比如视频或者音频或者图片
     * @return 是否成功
     */
    String uploadMessageFile(MultipartFile multipartFile, Integer userId);
}
