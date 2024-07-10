package com.project.shelf.sub_payment;

import com.project.shelf.book.Book;
import com.project.shelf.sub.Sub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubPaymentRepository extends JpaRepository<Sub, Integer> {
}
