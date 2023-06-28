package io.danielegradassai.service.impl;

import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.dto.comment.CommentInputDto;
import io.danielegradassai.dto.comment.CommentOutputDto;
import io.danielegradassai.entity.Article;
import io.danielegradassai.entity.Comment;
import io.danielegradassai.entity.User;
import io.danielegradassai.repository.ArticleRepository;
import io.danielegradassai.repository.CommentRepository;
import io.danielegradassai.repository.UserRepository;
import io.danielegradassai.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentOutputDto create(CommentInputDto commentInputDto) {
        User user = userRepository.findById(commentInputDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        Article article = articleRepository.findById(commentInputDto.getArticleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));

        Comment parentComment = null;
        if (commentInputDto.getParentCommentId() != null) {
            parentComment = commentRepository.findById(commentInputDto.getParentCommentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commento non trovato"));

        }
        Comment comment = new Comment(commentInputDto.getContent() , user, article, parentComment);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentOutputDto.class);
    }

    @Override
    public CommentOutputDto createAnotherComment(Long parentCommentId, CommentInputDto commentInputDto) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commento Genitore non trovato"));

        User user = userRepository.findById(commentInputDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User non trovato"));

        Article article = articleRepository.findById(commentInputDto.getArticleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato"));

        Comment anotherComment = new Comment(commentInputDto.getContent(), user, article);
        anotherComment.setParentComment(parentComment);

        parentComment.getAnotherComments().add(anotherComment);

        Comment savedChildComment = commentRepository.save(anotherComment);

        return modelMapper.map(savedChildComment, CommentOutputDto.class);
    }

    @Override
    public List<CommentOutputDto> getCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentOutputDto.class)).toList();
    }

    @Override
    public List<CommentOutputDto> getAnotherCommentByParentId(Long parentId) {
        Comment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commento genitore non trovato"));

        List<Comment> childComments = parentComment.getAnotherComments();
        return childComments.stream()
                .map(comment -> modelMapper.map(comment, CommentOutputDto.class)).toList();
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commento non trovato"));

        commentRepository.delete(comment);
    }

    @Override
    public void deleteAnotherComment(Long childCommentId) {
        Comment anotherComment = commentRepository.findById(childCommentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commento figlio non trovato"));

        Comment parentComment = anotherComment.getParentComment();
        parentComment.getAnotherComments().remove(anotherComment);

        commentRepository.delete(anotherComment);
    }



}
