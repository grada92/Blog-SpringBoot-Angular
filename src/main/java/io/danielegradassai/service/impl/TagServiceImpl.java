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
import org.springframework.stereotype.Service;

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

}
