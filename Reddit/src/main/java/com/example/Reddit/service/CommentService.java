package com.example.Reddit.service;

import com.example.Reddit.dto.CommentDto;
import com.example.Reddit.exceptions.PostNotFoundException;
import com.example.Reddit.mapper.CommentMapper;
import com.example.Reddit.model.Comment;
import com.example.Reddit.model.NotificationEmail;
import com.example.Reddit.model.Post;
import com.example.Reddit.model.User;
import com.example.Reddit.repository.CommentRepository;
import com.example.Reddit.repository.PostRepository;
import com.example.Reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Transactional
    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentDto,post, authService.getCurrentUser());
        commentRepository.save(comment);
        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on your post" + POST_URL);
        sendCommentNotification(message, post.getUser());
    }


    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper ::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
