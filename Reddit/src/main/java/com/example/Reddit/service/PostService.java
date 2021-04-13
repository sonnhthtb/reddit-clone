package com.example.Reddit.service;
import com.example.Reddit.dto.PostRequest;
import com.example.Reddit.dto.PostResponse;
import com.example.Reddit.exceptions.PostNotFoundException;
import com.example.Reddit.exceptions.SubredditNotFoundException;
import com.example.Reddit.mapper.PostMapper;
import com.example.Reddit.model.Post;
import com.example.Reddit.model.Subreddit;
import com.example.Reddit.model.User;
import com.example.Reddit.repository.PostRepository;
import com.example.Reddit.repository.SubredditRepository;
import com.example.Reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;


    public void save(PostRequest postRequest) {
       Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
               .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
       User currentUser = authService.getCurrentUser();
       postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
    }

    @Transactional(readOnly = true)
    public  PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"))
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public  List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit, Sort.by(Sort.Direction.DESC, "createdDate"));
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public  List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }
}
