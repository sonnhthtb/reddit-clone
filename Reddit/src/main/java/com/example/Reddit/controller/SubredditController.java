package com.example.Reddit.controller;

import com.example.Reddit.dto.SubredditDto;
import com.example.Reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
    }

    @PostMapping("/check-subreddit")
    public ResponseEntity<String> checkSubreddit(@RequestBody SubredditDto subredditDto){
        if(!subredditService.checkSubredditByName(subredditDto.getName())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Subreddit already exists");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }
}
