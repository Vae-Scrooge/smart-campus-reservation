package com.smartcampus.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(length = 200)
    private String location;

    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer capacity = 1;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'AVAILABLE'")
    private String status = "AVAILABLE";

    @Column(length = 5)
    private String openTime = "08:00";

    @Column(length = 5)
    private String closeTime = "22:00";

    private String createdAt;
    private String updatedAt;
}
