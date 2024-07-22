package com.project.shelf.payment;

import com.project.shelf.payment.PaymentResponseRecord.MonthlySaleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    // 월 매출
    @Query("""
            SELECT new com.project.shelf.payment.PaymentResponseRecord.MonthlySaleDTO(YEAR(p.createdAt), MONTH(p.createdAt), SUM(p.amount), COUNT(p))
            FROM Payment p
            GROUP BY YEAR(p.createdAt), MONTH(p.createdAt)
            ORDER BY YEAR(p.createdAt), MONTH(p.createdAt)
            """)
    List<MonthlySaleDTO> findSalesByMonth();

    // 특정 회원의 결제 내역 리스트
    @Query("SELECT p FROM Payment p JOIN FETCH p.subTypes s WHERE p.user.id = :userId")
    List<Payment> findByUserId(@Param("userId") Integer userId);

    // 특정 회원의 결제 내역 수
    @Query("SELECT count(*) FROM Payment p WHERE p.user.id = :userId")
    Integer countByUserId(@Param("userId") Integer userId);

    @Query("""
            SELECT p
            FROM Payment p
            WHERE p.user.id = :id
            ORDER BY p.createdAt DESC
            """)
    List<Payment> findLastPaymentById(@Param("id") Integer id);
}
