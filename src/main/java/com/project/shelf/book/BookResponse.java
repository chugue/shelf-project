package com.project.shelf.book;

import com.project.shelf.author.Author;
import com.project.shelf.wishlist.Wishlist;
import lombok.Data;

import java.time.LocalDateTime;

public class BookResponse {
    // 책 상세페이지
    @Data
    public static class DetailPageDTO{
        private Integer id;
        private String path;        // 책 사진 경로
        private String title;       // 책 제목
        private Author author;      // 저자
        private String publisher;   // 출판사
        private Book.Category category;
                                    // 카테고리
        private LocalDateTime createdAt;
                                    // 출판일?, 등록일?
        private String bookIntro;   // 책 소개
        private String contentIntro;// 내용 소개
        private Boolean isWish;     // 서재에 담겼는지
        // TODO : epub 경로 추가

        public DetailPageDTO(Book book, Boolean isWish) {
            this.id = book.getId();
            this.path = book.getPath();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.category = book.getCategory();
            this.createdAt = book.getCreatedAt();
            this.bookIntro = book.getBookIntro();
            this.contentIntro = book.getContentIntro();
            this.isWish = isWish;
        }
    }
}
