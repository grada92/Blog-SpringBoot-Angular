package io.danielegradassai.controller;

import io.danielegradassai.dto.comment.CommentInputDto;
import io.danielegradassai.dto.comment.CommentOutputDto;
import io.danielegradassai.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentOutputDto> create(@RequestBody CommentInputDto commentInputDto) {
        CommentOutputDto createdComment = commentService.create(commentInputDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PostMapping("/{parentCommentId}/comments")
    public ResponseEntity<CommentOutputDto> createAnotherComment(
            @PathVariable Long parentCommentId,
            @RequestBody CommentInputDto commentInputDto) {
        CommentOutputDto createdComment = commentService.createAnotherComment(parentCommentId, commentInputDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentOutputDto> comments = commentService.getCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/{parentId}")
    public ResponseEntity<List<CommentOutputDto>> getChildCommentsByParentId(@PathVariable("parentId") Long parentId) {
        List<CommentOutputDto> childComments = commentService.getAnotherCommentByParentId(parentId);
        return ResponseEntity.ok(childComments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comments/{anotherCommentId}")
    public ResponseEntity<Void> deleteAnotherComment(@PathVariable Long anotherCommentId) {
        commentService.deleteAnotherComment(anotherCommentId);
        return ResponseEntity.noContent().build();
    }
}
