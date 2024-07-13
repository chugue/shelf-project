package com.project.shelf._core.util;

import com.project.shelf._core.erros.exception.Exception500;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class MyFileUtil {

    public static String write(String imgBase64, String folder) {
        try {
            // 1. 파일명 생성
            UUID uuid = UUID.randomUUID();
            String mimeType = Base64Util.getMimeType(imgBase64);
            String imgFilename = folder + uuid + "." + mimeType;

            // 2. base64 -> byte[]
            byte[] imgBytes = Base64Util.decodeAsBytes(imgBase64);

            // 3. 파일 쓰기
            Path imgFilePath = Paths.get("." + imgFilename);
            Files.write(imgFilePath, imgBytes);
            return imgFilename;
        } catch (Exception e) {
            throw new Exception500("이미지 저장 오류 : " + e.getMessage());
        }
    }

    public static String write(MultipartFile file, String folder) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // MultipartFile을 Base64로 변환
        byte[] bytes = file.getBytes();
        String base64 = java.util.Base64.getEncoder().encodeToString(bytes);
        String mimeType = file.getContentType().split("/")[1];
        String imgBase64 = "data:image/" + mimeType + ";base64," + base64;

        // 파일 저장
        return write(imgBase64, folder);
    }
}
