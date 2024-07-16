package com.project.shelf.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("""
            SELECT p
            FROM Payment p
            WHERE p.user.id = :id
            ORDER BY p.createdAt DESC
            """)
    List<Payment> findLastPaymentById(@Param("id") Integer id);
}
