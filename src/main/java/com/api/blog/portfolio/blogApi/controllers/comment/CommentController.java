package com.api.blog.portfolio.blogApi.controllers.comment;


import com.api.blog.portfolio.blogApi.controllers.comment.dtos.RequestCommentDto;
import com.api.blog.portfolio.blogApi.controllers.comment.dtos.RequestGenericCommentDto;
import com.api.blog.portfolio.blogApi.controllers.comment.dtos.ResponseCommentDto;
import com.api.blog.portfolio.blogApi.entities.Comment;
import com.api.blog.portfolio.blogApi.services.comment.CommentService;
import jakarta.transaction.Transactional;
import org.hibernate.engine.spi.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping()
    public ResponseEntity<ResponseCommentDto> create(@RequestBody RequestCommentDto data, @RequestHeader("Authorization") String authToken) throws Exception {
        Comment comment = commentService.createComment(data, authToken);
        ResponseCommentDto responseCommentDto = new ResponseCommentDto(comment);
        return ResponseEntity.ok().body(responseCommentDto);
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody RequestGenericCommentDto data, @RequestHeader("Authorization") String authToken) throws Exception {
        Comment comment = commentService.update(data, authToken);
        return ResponseEntity.ok().body(new ResponseCommentDto(comment));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") String idComment, @RequestHeader("Authorization") String authToken) throws Exception {
        //commentService.validUserComment(idComment, authToken);
        Comment result = commentService.delete(idComment, authToken);
        return ResponseEntity.ok().body(result);
    }
}
