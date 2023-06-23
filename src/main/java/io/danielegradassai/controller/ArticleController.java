package io.danielegradassai.controller;

import io.danielegradassai.dto.article.ArticleInputDto;
import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleOutputDto> create(@RequestBody ArticleInputDto articleInputDto, @RequestParam Long userId) {
        return new ResponseEntity<>(articleService.create(articleInputDto, userId), HttpStatus.CREATED);
    }

}
