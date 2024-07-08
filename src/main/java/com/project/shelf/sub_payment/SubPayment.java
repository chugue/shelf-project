package com.project.shelf.sub_payment;

import com.project.shelf.sub.Sub;
import com.project.shelf.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.concurrent.Flow;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "sub_payment_tb")
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
    private SubscriptionPayment subscriptionPayment; //완료, 취소, 환불

    private LocalDate createdAt;
    private LocalDate updatedAt;



    public enum SubscriptionPayment {
        완료,취소,환불
    }
}
