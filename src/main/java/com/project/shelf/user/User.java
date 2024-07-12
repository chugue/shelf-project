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

    @Builder
    public User(Integer id, String email, String password, String nickName, String phone, String address, LocalDate createdAt, LocalDate updatedAt, boolean status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
