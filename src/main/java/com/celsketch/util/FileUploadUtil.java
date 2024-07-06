package com.celsketch.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUploadUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        File uploadDirPath = new File(uploadDir);
        if (!uploadDirPath.exists()) {
            uploadDirPath.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        String[] allowedExt = {".jpg", ".jpeg", ".png", ".gif"};

        boolean validExt = false;
        for (String e : allowedExt) {
            if (ext.equals(e)) {
                validExt = true;
                break;
            }
        }

        if (!validExt) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다.");
        }

        String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
        String newFileName = now + ext;

        File saveFile = new File(uploadDirPath, newFileName);
        file.transferTo(saveFile);

        return newFileName;
    }
}