package io.danielegradassai.service.impl;

import io.danielegradassai.dto.article.ArticleInputDto;
import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.entity.Article;
import io.danielegradassai.entity.Category;
import io.danielegradassai.entity.Tag;
import io.danielegradassai.entity.User;
import io.danielegradassai.exception.CustomValidationException;
import io.danielegradassai.repository.ArticleRepository;
import io.danielegradassai.repository.CategoryRepository;
import io.danielegradassai.repository.TagRepository;
import io.danielegradassai.repository.UserRepository;
import io.danielegradassai.service.ArticleService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;
    @Override
    public ArticleOutputDto create(ArticleInputDto articleInputDto, Long userId) {
        Set<ConstraintViolation<ArticleInputDto>> errors = validator.validate(articleInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        List<Category> categories = categoryRepository.findAllById(articleInputDto.getCategories());
        List<Tag> tags = tagRepository.findAllById(articleInputDto.getTags());
        Article article = new Article();
        article.setTitle(articleInputDto.getTitle());
        article.setContent(articleInputDto.getContent());
        article.setUser(user);
        article.setCategories(categories);
        article.setTags(tags);

        if (articleInputDto.getImage() != null && !articleInputDto.getImage().isEmpty()) {
            try {
                byte[] imageBytes = articleInputDto.getImage().getBytes();
                article.setImage(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Errore immagine", e);
            }
        }

        Article finalArticle = articleRepository.save(article);
        return modelMapper.map(finalArticle, ArticleOutputDto.class);

    }
}
