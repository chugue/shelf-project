package com.project.shelf.sub_types;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "sub_typs_tb")
public class SubTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer amount; //구독권 별 금액

    @Enumerated(EnumType.STRING)
    private SubscriptionPeriod subPeriod; //구독기간


    @Builder
    public SubTypes(Integer id, Integer amount,  SubscriptionPeriod subPeriod) {
        this.id = id;
        this.amount = amount;
        this.subPeriod = subPeriod;
    }

    public enum SubscriptionPeriod {
        일개월, 일년;
    }
}
