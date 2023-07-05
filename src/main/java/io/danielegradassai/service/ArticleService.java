package io.danielegradassai.service;

import io.danielegradassai.dto.article.ArticleInputDto;
import io.danielegradassai.dto.article.ArticleOutputDto;

import java.util.List;

public interface ArticleService {

    ArticleOutputDto create(ArticleInputDto articleInputDto);

    List<ArticleOutputDto> readAll();

    List<ArticleOutputDto> readAllApproved();

    List<ArticleOutputDto> readAllUnapproved();


    ArticleOutputDto updateApproved(Long articleId);

    ArticleOutputDto findById(Long id);

    List<ArticleOutputDto> getMostLikedArticles();

    void delete(Long articleId);
}
