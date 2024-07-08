package com.project.shelf.book;

import com.project.shelf.author.Author;
import com.project.shelf.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "book_tb")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Wishlist> wishlist;
    private String title;
    private String fileName; //사진이름
    private String path;//사진 경로
    private String bookIntro;
    private String index; //목차
    private String pageCount; // 총 페이지 수
    private String contents; // 책 내용 todo 추후에 맞게 수정

    @Enumerated(EnumType.STRING)
    private Category category; //소설, 자기개발, 역사

    private String publisher; //출판사
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Builder
    public Book(Integer id, Author author, List<Wishlist> wishlist, String title, String fileName, String path, String bookIntro, String index, String pageCount, String contents, Category category, String publisher, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.author = author;
        this.wishlist = wishlist;
        this.title = title;
        this.fileName = fileName;
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
        소설, 자기개발, 역사
    }
}
