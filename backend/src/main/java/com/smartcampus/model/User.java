package com.smartcampus.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String realName;

    @Column(name = "student_id", unique = true, length = 20)
    private String studentId;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'USER'")
    private String role = "USER";

    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer creditScore = 100;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    private String createdAt;
    private String updatedAt;
}
