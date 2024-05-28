package com.api.blog.portfolio.blogApi.entities;

import com.api.blog.portfolio.blogApi.controllers.publication.dtos.RequestPublicationDto;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @ElementCollection
    @Column(nullable = false)
    private List<String> subjects;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Publication(RequestPublicationDto data, User user) {
        this.title = data.title();
        this.content = data.content();
        this.url = data.url();
        this.description = data.description();
        this.subjects = data.subjects(); // Supondo que subject seja uma String
        this.createdAt = LocalDateTime.now(); // Data de criação definida para o momento atual
        this.user = user; // Associa o usuário passado
    }

}
