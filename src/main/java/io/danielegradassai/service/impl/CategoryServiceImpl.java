package io.danielegradassai.service.impl;

import io.danielegradassai.dto.category.CategoryInputDto;
import io.danielegradassai.dto.category.CategoryOutputDto;
import io.danielegradassai.entity.Category;
import io.danielegradassai.exception.CustomValidationException;
import io.danielegradassai.repository.CategoryRepository;
import io.danielegradassai.service.CategoryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Override
    public CategoryOutputDto create(CategoryInputDto categoryInputDto) {
        Set<ConstraintViolation<CategoryInputDto>> errors = validator.validate(categoryInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        return modelMapper.map(categoryRepository.save(modelMapper.map(categoryInputDto, Category.class)), CategoryOutputDto.class);
    }

    @Override
    public List<CategoryOutputDto> readAll() {
        return categoryRepository.findAll()
                .stream()
                .map(cat -> modelMapper.map(cat, CategoryOutputDto.class))
                .toList();
    }

    @Override
    public CategoryOutputDto readById(Long id) {
        return categoryRepository.findById(id)
                .map(cat -> modelMapper.map(cat, CategoryOutputDto.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria non trovata"));
    }

    @Override
    public CategoryOutputDto update(CategoryInputDto categoryInputDto, Long id) {
        Category cat = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria non trovata"));
        Set<ConstraintViolation<CategoryInputDto>> errors = validator.validate(categoryInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        cat.setName(categoryInputDto.getName());
        return modelMapper.map(categoryRepository.save(cat), CategoryOutputDto.class);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria non trovata");
        }
        categoryRepository.deleteById(id);
    }
}
