package com.project.shelf.user;

import com.project.shelf.book_history.BookHistory;
import com.project.shelf.payment.Payment;
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
@Table(name =  "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String nickName;
    private String phone;
    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private boolean status; // 구독 상태

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Payment> subPayments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BookHistory> bookHistories;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Wishlist> wishlist;


    @Builder
    public User(Integer id, String email, String password, String nickName, String phone, String address, LocalDate createdAt, LocalDate updatedAt, List<Payment> subPayments, List<BookHistory> bookHistories, List<Wishlist> wishlist, boolean status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subPayments = subPayments;
        this.bookHistories = bookHistories;
        this.wishlist = wishlist;
        this.status = status;
    }
}
