package io.danielegradassai.service;

import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.dto.comment.CommentInputDto;
import io.danielegradassai.dto.comment.CommentOutputDto;

import java.util.List;

public interface CommentService {
    CommentOutputDto create(CommentInputDto commentInputDto);

    CommentOutputDto createAnotherComment(Long parentCommentId, CommentInputDto commentInputDto);

    List<CommentOutputDto> getCommentsByArticleId(Long articleId);

    List<CommentOutputDto> getAnotherCommentByParentId(Long parentId);

    void delete(Long commentId);

    void deleteUserComment(Long commentId, Long userId);

    void deleteAnotherComment(Long childCommentId);
}
