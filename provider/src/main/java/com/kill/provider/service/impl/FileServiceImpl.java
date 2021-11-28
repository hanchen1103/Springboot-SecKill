package com.kill.provider.service.impl;

import com.kill.api.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.UUID;

public class FileServiceImpl implements FileService {

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
            dest.getParentFile().mkdirs();
        }
        multipartFile.transferTo(dest);
        return fileName;
    }

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
