package com.project.shelf.book;

import com.project.shelf.author.Author;
import com.project.shelf.book_history.BookHistory;
import com.project.shelf.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Data
@Table(name = "book_tb")
@EntityListeners(AuditingEntityListener.class)  // 엔티티 리스너 추가
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Wishlist> wishlist;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BookHistory> bookHistory;

    private String title;
    private String path; // 사진 경로

    private Integer pageCount; // 총 페이지 수

    private String bookIntro; // 책 소개
    private String contentIntro; // 내용 소개

    @Enumerated(EnumType.STRING)
    private Category category; // 소설, 자기계발, 역사

    private String publisher; // 출판사
    private String epubFile;

    //    @ColumnDefault("'2019-07-16'")
    private LocalDate registrationDate; // 출판일

    @ColumnDefault("527")
    private Integer totalView;
    @ColumnDefault("300")
    private Integer completedViews;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Book(Integer id, Author author, List<Wishlist> wishlist, List<BookHistory> bookHistory, String title, String path, Integer pageCount, String bookIntro, String contentIntro, Category category, String publisher, String epubFile, LocalDate registrationDate, Integer totalView, Integer completedViews, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.wishlist = wishlist;
        this.bookHistory = bookHistory;
        this.title = title;
        this.path = path;
        this.pageCount = pageCount;
        this.bookIntro = bookIntro;
        this.contentIntro = contentIntro;
        this.category = category;
        this.publisher = publisher;
        this.epubFile = epubFile;
        this.registrationDate = registrationDate;
        this.totalView = totalView;
        this.completedViews = completedViews;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum Category {
        소설, 자기계발, 역사, 인문, 사회, 과학, 철학, 종교, IT;

        public static List<String> getCategories() {
            return Arrays.stream(Category.values())
                    .map(Category::name)
                    .collect(Collectors.toList());
        }
    }
}
