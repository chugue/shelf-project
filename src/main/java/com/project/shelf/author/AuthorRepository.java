package com.project.shelf.author;

import com.project.shelf.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
