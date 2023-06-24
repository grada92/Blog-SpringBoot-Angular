package io.danielegradassai.service;

import io.danielegradassai.dto.article.ArticleInputDto;
import io.danielegradassai.dto.article.ArticleOutputDto;

import java.util.List;

public interface ArticleService {
    ArticleOutputDto create(ArticleInputDto articleInputDto);

    List<ArticleOutputDto> readAll();

    ArticleOutputDto findById(Long id);
}
