package com.example.demo.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @Column(name = "login", unique = true)
    private String login;

    @Nonnull
    @Column(name = "password")
    private String password;

    @Nonnull
    @Column(name = "role")
    @ColumnDefault("USER")
    private String role;
}