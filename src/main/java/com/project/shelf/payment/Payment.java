package com.project.shelf.payment;

import com.project.shelf.sub_types.SubTypes;
import com.project.shelf.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Data
@Table(name = "payment_tb")
@EntityListeners(AuditingEntityListener.class)  // 엔티티 리스너 추가
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubTypes subTypes;

    private String orderDate; // 결제 시간

    private Integer amount; //결제 금액

    @Enumerated(EnumType.STRING)
    private SubscriptionPayment subStatus; //완료, 취소, 환불

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Builder
    public Payment(Integer id, User user, SubTypes subTypes, String orderDate, Integer amount, SubscriptionPayment subStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.subTypes = subTypes;
        this.orderDate = orderDate;
        this.amount = amount;
        this.subStatus = subStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum SubscriptionPayment {
        완료,취소,환불
    }
}
