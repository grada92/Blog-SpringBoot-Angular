package io.danielegradassai.controller;

import io.danielegradassai.dto.category.CategoryInputDto;
import io.danielegradassai.dto.category.CategoryOutputDto;
import io.danielegradassai.dto.tag.TagInputDto;
import io.danielegradassai.dto.tag.TagOutputDto;
import io.danielegradassai.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryOutputDto> create(@RequestBody CategoryInputDto categoryInputDto){
        return new ResponseEntity<>(categoryService.create(categoryInputDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryOutputDto>> readAll(){
        List<CategoryOutputDto> cat = categoryService.readAll();
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryOutputDto> readById(@PathVariable Long id){
        CategoryOutputDto categoryOutputDto = categoryService.readById(id);
        return new ResponseEntity<>(categoryOutputDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryOutputDto> update(@RequestBody CategoryInputDto categoryInputDto, @PathVariable Long id){
        CategoryOutputDto categoryOutputDto = categoryService.update(categoryInputDto,id);
        return new ResponseEntity<>(categoryOutputDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryOutputDto> delete(@PathVariable Long id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
