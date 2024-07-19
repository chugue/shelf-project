package com.project.shelf.user;

import com.project.shelf._core.enums.Avatar;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "user_tb")
@EntityListeners(AuditingEntityListener.class)  // 엔티티 리스너 추가
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String nickName;
    private String phone;
    private String address;
    private String customerUid; // 구매자의 결제 수단 식별 고유번호 (ex.customer_1720595101182)

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @ColumnDefault("false") // 기본 값 false
    private Boolean status; // 구독 상태

    @Enumerated(EnumType.STRING)  // 플러터에서 쓸 아바타 이름
    private Avatar avatar;

    private String provider; // facebook, kakao, apple, naver

    @Builder
    public User(Integer id, String email, String password, String nickName, String phone, String address, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean status, Avatar avatar, String provider) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.avatar = avatar;
        this.provider = provider;
    }
}
