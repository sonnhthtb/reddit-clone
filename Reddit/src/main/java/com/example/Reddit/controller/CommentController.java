package com.example.Reddit.controller;


import com.example.Reddit.dto.CommentDto;
import com.example.Reddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        commentService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentForPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentForPost(postId));
    }

    @GetMapping("by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentForUser(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentForUser(userName));
    }
}
