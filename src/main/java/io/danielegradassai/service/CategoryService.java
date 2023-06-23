package io.danielegradassai.service;

import io.danielegradassai.dto.category.CategoryInputDto;
import io.danielegradassai.dto.category.CategoryOutputDto;

import java.util.List;

public interface CategoryService {
    CategoryOutputDto create(CategoryInputDto categoryInputDto);

    List<CategoryOutputDto> readAll();

    CategoryOutputDto readById(Long id);

    CategoryOutputDto update(CategoryInputDto categoryInputDto, Long id);

    void delete(Long id);
}
