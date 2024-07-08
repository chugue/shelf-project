package com.project.shelf.book;

import com.project.shelf.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
