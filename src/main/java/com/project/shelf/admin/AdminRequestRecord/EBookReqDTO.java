package com.project.shelf.admin.AdminRequestRecord;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record EBookReqDTO(
        // 책 제목
        String title,

        // 저자
        String author,

        // 출판사
        String publisher,

        // 카테고리
        String category,

        // 코멘트
        String comment,

        // 책 소개
        String introduction,

        // 목차
        String toc,

        // 책 표지 사진 (MultipartFile 타입)
        MultipartFile mainPhoto,

        // eBook 파일 (MultipartFile 타입)
        MultipartFile ebookFile
) {
}
