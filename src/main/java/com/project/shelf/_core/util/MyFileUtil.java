package com.project.shelf._core.util;



import com.project.shelf._core.erros.exception.Exception500;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class MyFileUtil {

    public static String write(String imgBase64) {
        try {
            // 1. file folder path
            String folder = "/images/";

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
}
