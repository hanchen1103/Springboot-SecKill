package com.kill.consumer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final String[] suffixNameTable = {"jpg", "bmp", "jpeg", "webbp", "pcx", "tif", "gif",
            "tga", "exif", "fpx", "svg", "psd", "cdr", "pcd", "dxf", "ufo", "eps", "ai",
            "png", "hdri", "raw", "wmf", "flic", "emf", "ico"};

    public static boolean isPic(String suffixName) {
        if(suffixName == null || suffixName.isBlank()) {
            return false;
        }
        String target  = suffixName.toLowerCase().split("\\.")[1];
        for(String i : suffixNameTable) {
            if(target.equals(i)) {
                return true;
            }
        }
        return false;
    }
}
