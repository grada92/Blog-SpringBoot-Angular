package io.danielegradassai.controller;

import io.danielegradassai.dto.validation.ValidationInputDto;
import io.danielegradassai.dto.validation.ValidationOutputDto;
import io.danielegradassai.entity.ValidationAdmin;
import io.danielegradassai.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/validation")
public class ValidationController {

    private final ValidationService validationService;

    @PostMapping("/create")
    public ResponseEntity<ValidationOutputDto> createValidation(@RequestBody @Valid ValidationInputDto validationInputDto) {
        ValidationOutputDto createdValidation = validationService.create(validationInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdValidation);
    }

    @PutMapping("/update")
    public ResponseEntity<ValidationOutputDto> updateValidation(@RequestBody ValidationInputDto validationInputDto) {
        ValidationOutputDto validationOutputDto = validationService.update(validationInputDto);
        return ResponseEntity.ok(validationOutputDto);
    }

    @GetMapping
    public ValidationAdmin getValidationAdmin() {
       return validationService.getValidationAdmin();
    }


}
