package com.api.blog.portfolio.blogApi.entities;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "publications")
@Table(name = "publications")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(unique = true, nullable = false)
    private String url;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    //a proriedade a baixo pode ser um enum     @Enumerated(EnumType.STRING)
    private String subject;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
