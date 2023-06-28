package io.danielegradassai.service;

import io.danielegradassai.dto.validation.ValidationInputDto;
import io.danielegradassai.dto.validation.ValidationOutputDto;
import io.danielegradassai.entity.Article;
import io.danielegradassai.entity.ValidationAdmin;

import java.util.List;

public interface ValidationService {

    ValidationOutputDto create(ValidationInputDto validationInputDto);

    ValidationOutputDto update(ValidationInputDto validationInputDto);

    ValidationAdmin getValidationAdmin();
}
