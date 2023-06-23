package io.danielegradassai.service;

import io.danielegradassai.dto.article.ArticleInputDto;
import io.danielegradassai.dto.article.ArticleOutputDto;

public interface ArticleService {
    ArticleOutputDto create(ArticleInputDto articleInputDto, Long userId);
}
