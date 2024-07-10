package com.project.shelf.sub_payment;

import com.project.shelf.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubPaymentRepository extends JpaRepository<SubPayment, Integer> {
}
