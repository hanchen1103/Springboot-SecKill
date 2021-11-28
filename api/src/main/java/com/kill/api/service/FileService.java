package com.kill.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    /**
     * 上传文件
     * @param multipartFile 多媒体文件
     * @param filePath 存放路径
     */
    String uploadFile(MultipartFile multipartFile, String filePath) throws IOException;

    /**
     * 获取文件后缀名
     * @param file 文件名称
     * @return suffixName后缀名
     */
    String getSuffixName(MultipartFile file) throws FileNotFoundException;
}
