package com.project.shelf.sub_payment;

import com.project.shelf.sub.Sub;
import com.project.shelf.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Flow;

@NoArgsConstructor
@Entity
@Data
@Table(name = "sub_payment_tb")
public class SubPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sub sub;

    private String orderDate; // 결제 시간

    private Integer amount; //결제 금액

    @Enumerated(EnumType.STRING)
    private SubscriptionPayment subPayment; //완료, 취소, 환불

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public SubPayment(Integer id, User user, Sub sub, String orderDate, Integer amount, SubscriptionPayment subPayment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.sub = sub;
        this.orderDate = orderDate;
        this.amount = amount;
        this.subPayment = subPayment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum SubscriptionPayment {
        완료,취소,환불
    }
}
