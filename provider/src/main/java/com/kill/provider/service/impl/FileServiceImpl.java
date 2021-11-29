package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    /**
     * 上传文件
     * @param multipartFile 多媒体文件
     * @param filePath 存放路径
     * @return 文件名称
     * @throws IOException 录入文件错误
     */
    @Override
    public String uploadFile(MultipartFile multipartFile, String filePath) throws IOException {
        if(multipartFile == null || multipartFile.isEmpty()) {
            throw new FileNotFoundException();
        }
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if(!dest.getParentFile().exists()) {
            Boolean res = dest.getParentFile().mkdirs();
        }
        multipartFile.transferTo(dest);
        return fileName;
    }

    /**
     * 获取后缀名称
     * @param file 文件名称
     * @return 后缀名称
     * @throws FileNotFoundException 未找到文件
     */
    @Override
    public String getSuffixName(MultipartFile file) throws FileNotFoundException {
        if(file == null || file.isEmpty()) {
            throw new FileNotFoundException();
        }
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
