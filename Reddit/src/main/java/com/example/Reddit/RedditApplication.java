package com.example.Reddit;

import com.example.Reddit.config.SwaggerConfiguration;
import io.swagger.models.Swagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class RedditApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedditApplication.class, args);
	}

}
