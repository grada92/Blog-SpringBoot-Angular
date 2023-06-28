package io.danielegradassai.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentOutputDto {

    private Long id;
    private String content;
    private Long userId;
    private Long articleId;
    private Long parentCommentId;
}
