package com.project.shelf.book_history;

import com.project.shelf.book.Book;
import com.project.shelf.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "book_history_tb")
public class BookHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private String lastReadPage; //마지막으로 읽은 페이지
    private LocalDate createdAt; //처음 읽은 날짜
    private LocalDate updatedAt; //마지막으로 읽은 날짜

    @Builder
    public BookHistory(Integer id, User user, Book book, String lastReadPage, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.lastReadPage = lastReadPage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
