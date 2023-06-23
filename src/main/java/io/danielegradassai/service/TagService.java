package io.danielegradassai.service;

import io.danielegradassai.dto.tag.TagInputDto;
import io.danielegradassai.dto.tag.TagOutputDto;

import java.util.List;

public interface TagService {
    TagOutputDto create(TagInputDto tagInputDto);

    List<TagOutputDto> readAll();
}
