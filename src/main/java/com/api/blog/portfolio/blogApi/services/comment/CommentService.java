package com.api.blog.portfolio.blogApi.services.comment;

import com.api.blog.portfolio.blogApi.controllers.comment.dtos.RequestCommentDto;
import com.api.blog.portfolio.blogApi.controllers.comment.dtos.RequestGenericCommentDto;
import com.api.blog.portfolio.blogApi.entities.Comment;
import com.api.blog.portfolio.blogApi.entities.Publication;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.ResourceNotFoundException;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.UnauthorizedAccessException;
import com.api.blog.portfolio.blogApi.infra.security.SecurityFilter;
import com.api.blog.portfolio.blogApi.repositories.comment.CommentRepositorie;
import com.api.blog.portfolio.blogApi.repositories.publication.PublicationRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepositorie commentRepositorie;
    @Autowired
    SecurityFilter securityFilter;
    @Autowired
    PublicationRepositorie publicationRepositorie;

    public void validUserComment(String idComment, String token) throws UnauthorizedAccessException {
        User user = securityFilter.authToken(token);
        boolean isValid = user.getComments().stream()
                .anyMatch(comment -> comment.getId().equals(idComment));
        if (!isValid) {
            throw new UnauthorizedAccessException();
        }
    }

    //CRUD
    public Comment createComment(RequestCommentDto data, String token) throws ResourceNotFoundException {
        User user = securityFilter.authToken(token);
        Optional<Publication> publication = publicationRepositorie.findById(data.idPublication());
        if (!publication.isPresent()){
            throw new ResourceNotFoundException();
        }
        Comment newComment = new Comment(data.content(), user, publication.get());
        return commentRepositorie.save(newComment);
    }
    //READ
    public Comment update(RequestGenericCommentDto data, String token) throws ResourceNotFoundException {
        validUserComment(data.commentId(), token);
        Optional<Comment> commentOpt = commentRepositorie.findById(data.commentId());
        if (commentOpt.isPresent()) {
            Comment updateComment = commentOpt.get();
            updateComment.setContent(data.content());
            updateComment.setUpdatedAt(LocalDateTime.now());
            return commentRepositorie.save(updateComment);
        } else {
            throw new ResourceNotFoundException();
        }
    }
    public Comment delete(String idComment, String token) throws UnauthorizedAccessException {
        User user = securityFilter.authToken(token);

        Optional<Comment> comment = commentRepositorie.findById(idComment);

        if (user.getId() != comment.get().getUser().getId()){
            throw new UnauthorizedAccessException();
        }

        commentRepositorie.delete(comment.get());
        return comment.get();
    }



}
