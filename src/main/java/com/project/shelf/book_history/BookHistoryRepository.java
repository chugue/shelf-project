package com.project.shelf.book_history;

import com.project.shelf.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookHistoryRepository extends JpaRepository<BookHistory, Integer> {
}
