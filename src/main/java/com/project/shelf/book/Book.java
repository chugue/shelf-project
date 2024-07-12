package com.project.shelf.book;

import com.project.shelf.author.Author;
import com.project.shelf.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "book_tb")
@EntityListeners(AuditingEntityListener.class)  // 엔티티 리스너 추가
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    private String title;
    private String path; // 사진 경로
    private String bookIntro;

    @Column(columnDefinition = "TEXT")
    private String index; // 목차 (여러 개의 목차를 JSON 문자열로 저장)

    private String pageCount; // 총 페이지 수
    private String contents; // 책 내용 todo 추후에 맞게 수정

    @Enumerated(EnumType.STRING)
    private Category category; // 소설, 자기계발, 역사

    private String publisher; // 출판사
    private String epubFile;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Book(Integer id, Author author, String title, String path, String bookIntro, String index, String pageCount, String contents, Category category, String publisher, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.path = path;
        this.bookIntro = bookIntro;
        this.index = index;
        this.pageCount = pageCount;
        this.contents = contents;
        this.category = category;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum Category {
        소설, 자기계발, 역사
    }
}
