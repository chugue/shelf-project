package com.project.shelf.author;

import com.project.shelf.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findByName(String name);

    Optional<Author> findByAuthorIntro(String authorIntro);
}
