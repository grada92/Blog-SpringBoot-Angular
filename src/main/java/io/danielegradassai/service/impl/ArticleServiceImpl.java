package io.danielegradassai.service.impl;

import io.danielegradassai.dto.article.ArticleInputDto;
import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.entity.*;
import io.danielegradassai.exception.CustomValidationException;
import io.danielegradassai.repository.ArticleRepository;
import io.danielegradassai.repository.CategoryRepository;
import io.danielegradassai.repository.TagRepository;
import io.danielegradassai.repository.UserRepository;
import io.danielegradassai.service.ArticleService;
import io.danielegradassai.service.ValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    private final ValidationService validationService;
    @Override
    public ArticleOutputDto create(ArticleInputDto articleInputDto) {
        Set<ConstraintViolation<ArticleInputDto>> errors = validator.validate(articleInputDto);
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }

        // VALIDAZIONE TITOLO
        ValidationAdmin validationAdmin = validationService.getValidationAdmin();
        int maxTitleLength = validationAdmin.getMaxTitleLength();
        if (articleInputDto.getTitle().length() > maxTitleLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La lunghezza del titolo supera il limite consentito");
        }

        // VALIDAZIONE CONTENT
        ValidationAdmin validationAdmin1 = validationService.getValidationAdmin();
        int maxContentLength = validationAdmin.getMaxContentLength();
        String sanitizedContent = Jsoup.clean(articleInputDto.getContent(), Whitelist.none());
        if (sanitizedContent.length() > maxContentLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La lunghezza del contenuto supera il limite consentito");
        }

        User user = userRepository.findById(articleInputDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        List<Category> categories = categoryRepository.findAllById(articleInputDto.getCategories());
        List<Tag> tags = tagRepository.findAllById(articleInputDto.getTags());
        LocalDateTime createdAt = LocalDateTime.now();

        Article article = new Article();
        article.setTitle(articleInputDto.getTitle());
        article.setContent(articleInputDto.getContent());
        article.setUser(user);
        article.setCategories(categories);
        article.setTags(tags);
        article.setApproved(false);
        article.setCreatedAt(createdAt);
        article.setDislikeCount(0);
        article.setLikeCount(0);
        ValidationAdmin validation = validationService.getValidationAdmin();
        Article finalArticle = articleRepository.save(article);
        return modelMapper.map(finalArticle, ArticleOutputDto.class);
    }

    @Override
    public List<ArticleOutputDto> readAll() {
        return articleRepository.findAll()
                .stream()
                .map(article -> modelMapper.map(article, ArticleOutputDto.class)).toList();
    }

    @Override
    public List<ArticleOutputDto> readAllApproved() {
        return articleRepository.findByIsApprovedOrderByCreatedAtDesc(true)
                .stream()
                .map(article -> modelMapper.map(article, ArticleOutputDto.class))
                .toList();
    }


    @Override
    public List<ArticleOutputDto> readAllUnapproved() {
        return articleRepository.findByIsApproved(false)
                .stream()
                .map(article -> modelMapper.map(article, ArticleOutputDto.class))
                .toList();
    }

    @Override
    public ArticleOutputDto updateApproved(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));

        article.setApproved(true);
        Article updatedArticle = articleRepository.save(article);

        return modelMapper.map(updatedArticle, ArticleOutputDto.class);
    }

    @Override
    public ArticleOutputDto findById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));
        return modelMapper.map(article, ArticleOutputDto.class);
    }

    @Override
    public List<ArticleOutputDto> getMostLikedArticles() {
        return articleRepository.findTop5ByOrderByLikeCountDesc()
                .stream()
                .map(article -> modelMapper.map(article, ArticleOutputDto.class)).toList();
    }
    @Override
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));

        articleRepository.delete(article);
    }

}
