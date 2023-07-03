package io.danielegradassai.controller;

import io.danielegradassai.dto.article.ArticleInputDto;
import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleOutputDto> create(@RequestBody ArticleInputDto articleInputDto) {
        return new ResponseEntity<>(articleService.create(articleInputDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ArticleOutputDto>> readAll() {
        List<ArticleOutputDto> rentals = articleService.readAll();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ArticleOutputDto>> getApprovedArticles() {
        List<ArticleOutputDto> approvedArticles = articleService.readAllApproved();
        return new ResponseEntity<>(approvedArticles, HttpStatus.OK);
    }

    @GetMapping("/unapproved")
    public ResponseEntity<List<ArticleOutputDto>> getUnapprovedArticles() {
        List<ArticleOutputDto> unapprovedArticles = articleService.readAllUnapproved();
        return new ResponseEntity<>(unapprovedArticles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleOutputDto> getArticleById(@PathVariable Long id) {
        ArticleOutputDto article = articleService.findById(id);
        return ResponseEntity.ok(article);
    }

    @GetMapping("/top")
    public ResponseEntity<List<ArticleOutputDto>> getTopLikedArticles() {
        List<ArticleOutputDto> topLikedArticles = articleService.getMostLikedArticles();
        return new ResponseEntity<>(topLikedArticles, HttpStatus.OK);
    }

    @PutMapping("/approved/{articleId}")
    public ResponseEntity<ArticleOutputDto> approveArticle(@PathVariable Long articleId) {
        ArticleOutputDto approvedArticle = articleService.updateApproved(articleId);
        return ResponseEntity.ok(approvedArticle);
    }


}
