package com.project.shelf.wishlist;

import com.project.shelf.book.Book;
import com.project.shelf.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "wishlist_tb")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Wishlist(Integer id, User user, Book book) {
        this.id = id;
        this.user = user;
        this.book = book;
    }
}
