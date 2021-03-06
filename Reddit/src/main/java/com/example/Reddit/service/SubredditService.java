package com.example.Reddit.service;

import com.example.Reddit.dto.SubredditDto;
import com.example.Reddit.exceptions.SpringRedditException;
import com.example.Reddit.exceptions.SubredditNotFoundException;
import com.example.Reddit.mapper.SubredditMapper;
import com.example.Reddit.model.Subreddit;
import com.example.Reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit =  subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;

    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    @Transactional(readOnly = true)
    public boolean checkSubredditByName(String name) {
        Optional<Subreddit> subreddit = subredditRepository.findByName(name);
        if(subreddit.isPresent()) {
            return false;
        }
        return true;
    }

}
