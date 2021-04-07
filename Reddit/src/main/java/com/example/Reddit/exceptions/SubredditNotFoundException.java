package com.example.Reddit.exceptions;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String subredditName) {
        super(subredditName);
    }
}
