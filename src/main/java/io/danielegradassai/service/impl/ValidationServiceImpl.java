package io.danielegradassai.service.impl;

import io.danielegradassai.dto.validation.ValidationInputDto;
import io.danielegradassai.dto.validation.ValidationOutputDto;
import io.danielegradassai.entity.ValidationAdmin;
import io.danielegradassai.exception.CustomValidationException;
import io.danielegradassai.repository.ArticleRepository;
import io.danielegradassai.repository.ValidationAdminRepository;
import io.danielegradassai.service.ValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class ValidationServiceImpl implements ValidationService {
    private final ArticleRepository articleRepository;
    private final ValidationAdminRepository validationAdminRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Override
    public ValidationOutputDto create(ValidationInputDto validationInputDto) {
        Set<ConstraintViolation<ValidationInputDto>> errors = validator.validate(validationInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        return modelMapper.map(validationAdminRepository.save(modelMapper.map(validationInputDto, ValidationAdmin.class)), ValidationOutputDto.class);
    }

    @Override
    public ValidationOutputDto update(ValidationInputDto validationInputDto) {
        ValidationAdmin validationAdmin = validationAdminRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Validazione non trovata!"));
        Set<ConstraintViolation<ValidationInputDto>> errors = validator.validate(validationInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        validationAdmin.setMaxTitleLength(validationInputDto.getMaxTitleLength());
        validationAdmin.setMaxContentLength(validationInputDto.getMaxContentLength());
        return modelMapper.map(validationAdminRepository.save(validationAdmin), ValidationOutputDto.class);
}
    @Override
    public ValidationAdmin getValidationAdmin() {
        return validationAdminRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mancano regole di validazione"));

    }

}

