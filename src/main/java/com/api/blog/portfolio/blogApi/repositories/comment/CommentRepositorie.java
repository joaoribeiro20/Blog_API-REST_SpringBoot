package com.api.blog.portfolio.blogApi.repositories.comment;

import com.api.blog.portfolio.blogApi.entities.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface CommentRepositorie extends JpaRepository<Comment, String> {


}
