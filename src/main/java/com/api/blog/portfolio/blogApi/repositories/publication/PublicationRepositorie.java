package com.api.blog.portfolio.blogApi.repositories.publication;

import com.api.blog.portfolio.blogApi.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepositorie extends JpaRepository<Publication, String> {
}
