package io.danielegradassai.service.impl;

import io.danielegradassai.dto.tag.TagInputDto;
import io.danielegradassai.dto.tag.TagOutputDto;
import io.danielegradassai.entity.Tag;
import io.danielegradassai.exception.CustomValidationException;
import io.danielegradassai.repository.TagRepository;
import io.danielegradassai.service.TagService;
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
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Override
    public TagOutputDto create(TagInputDto tagInputDto) {
        Set<ConstraintViolation<TagInputDto>> errors = validator.validate(tagInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        return modelMapper.map(tagRepository.save(modelMapper.map(tagInputDto, Tag.class)), TagOutputDto.class);
    }

    @Override
    public List<TagOutputDto> readAll() {
        return tagRepository.findAll()
                .stream()
                .map(brand -> modelMapper.map(brand, TagOutputDto.class))
                .toList();
    }

    @Override
    public TagOutputDto readById(Long id) {
        return tagRepository.findById(id)
                .map(brand -> modelMapper.map(brand, TagOutputDto.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag non trovato"));
    }

    @Override
    public TagOutputDto update(TagInputDto tagInputDto, Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag non trovato"));
        Set<ConstraintViolation<TagInputDto>> errors = validator.validate(tagInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        tag.setName(tagInputDto.getName());
        return modelMapper.map(tagRepository.save(tag), TagOutputDto.class);
    }

    @Override
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag non trovato");
        }
        tagRepository.deleteById(id);
    }

}
