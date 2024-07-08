package com.project.shelf.author;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "author_tb")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String authorIntro;

    public Author(Integer id, String name, LocalDate createdAt, LocalDate updatedAt, String authorIntro) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorIntro = authorIntro;
    }
}
