package com.project.shelf.admin;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Data
@Table(name =  "admin_tb")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Builder
    public Admin(Integer id, String email, String password, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
