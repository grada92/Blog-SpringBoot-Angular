package io.danielegradassai.controller;

import io.danielegradassai.dto.tag.TagInputDto;
import io.danielegradassai.dto.tag.TagOutputDto;
import io.danielegradassai.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/tag")
public class TagController {

    private final TagService tagService;
    @PostMapping
    public ResponseEntity<TagOutputDto> create(@RequestBody TagInputDto tagInputDto){
        return new ResponseEntity<>(tagService.create(tagInputDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagOutputDto>> readAll(){
        List<TagOutputDto> brand = tagService.readAll();
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagOutputDto> readById(@PathVariable Long id){
        TagOutputDto tagOutputDto = tagService.readById(id);
        return new ResponseEntity<>(tagOutputDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TagOutputDto> update(@RequestBody TagInputDto tagInputDto, @PathVariable Long id){
        TagOutputDto tagOutputDto = tagService.update(tagInputDto,id);
        return new ResponseEntity<>(tagOutputDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TagOutputDto> delete(@PathVariable Long id){
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
