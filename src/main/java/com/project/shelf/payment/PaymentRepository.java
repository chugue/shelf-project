package com.project.shelf.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    // 특정 회원의 결제 내역 수
    @Query("SELECT count(*) FROM Payment p WHERE p.user.id = :userId")
    Integer findByUserId(@Param("userId") Integer userId);

    @Query("""
            SELECT p
            FROM Payment p
            WHERE p.user.id = :id
            ORDER BY p.createdAt DESC
            """)
    List<Payment> findLastPaymentById(@Param("id") Integer id);
}
