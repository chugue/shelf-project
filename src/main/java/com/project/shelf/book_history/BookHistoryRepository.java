package com.project.shelf.book_history;

import com.project.shelf.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookHistoryRepository extends JpaRepository<BookHistory, Integer> {
}
