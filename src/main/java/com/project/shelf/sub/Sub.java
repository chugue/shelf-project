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

    private String orderDate; //구독한 시간
    private Integer Amount; //결제 금액
    private boolean status; // 구독 상태
    private LocalDate createdAt; // DB에 들어간 시간
    private LocalDate updatedAt; // DB에 수정된 시간

    @Enumerated(EnumType.STRING)
    private SubscriptionPeriod sub; //구독기간

    @OneToMany(mappedBy = "sub", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SubPayment> subPayment;

    @Builder
    public Sub(Integer id, String orderDate, Integer amount, boolean status, LocalDate createdAt, LocalDate updatedAt, SubscriptionPeriod sub, List<SubPayment> subPayment) {
        this.id = id;
        this.orderDate = orderDate;
        Amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sub = sub;
        this.subPayment = subPayment;
    }

    public enum SubscriptionPeriod {
        일개월, 일년;
    }
}
