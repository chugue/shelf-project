package com.project.shelf.sub;

import com.project.shelf.sub_payment.SubPayment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "sub_tb")
public class Sub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount; //구독권 별 금액
    private boolean status; // 구독 상태


    @Enumerated(EnumType.STRING)
    private SubscriptionPeriod subPeriod; //구독기간


    @Builder
    public Sub(Integer id, Integer amount, boolean status, SubscriptionPeriod subPeriod) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.subPeriod = subPeriod;
    }

    public enum SubscriptionPeriod {
        일개월, 일년;
    }
}
