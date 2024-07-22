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

    private String impUid;      // 포트원 거래고유번호 (ex.imp_029113736026)
    private String merchantUid; // 고객사 주문번호 (ex.order_1720595101178)
    private String name;        // 제품명 (ex.1개월 정기 결제)
    private String customerUid; // 구매자의 결제 수단 식별 고유번호 (ex.customer_1720595101182)
    private String payMethod;   // 결제수단 구분코드 (ex.card)
    private String cardName;    // 카드사명 (ex.BC카드)
    private String cardNumber;  // 카드 번호
    private Integer amount;     //결제 금액
    private String orderDate;   // 결제 시각(ex.1720595225)

    @Enumerated(EnumType.STRING)
    private SubscriptionPayment subStatus; //완료, 취소, 환불

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Builder
    public Payment(Integer id, User user, SubTypes subTypes, String orderDate, Integer amount, String impUid, String payMethod, String merchantUid, String name, String cardName, String cardNumber, String customerUid, SubscriptionPayment subStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.subTypes = subTypes;
        this.orderDate = orderDate;
        this.amount = amount;
        this.impUid = impUid;
        this.payMethod = payMethod;
        this.merchantUid = merchantUid;
        this.name = name;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.customerUid = customerUid;
        this.subStatus = subStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum SubscriptionPayment {
        완료,취소,환불
    }
}
