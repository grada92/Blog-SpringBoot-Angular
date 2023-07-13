package io.danielegradassai.dto.subscription;

import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.dto.comment.CommentOutputDto;
import io.danielegradassai.dto.user.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationOutputDto {

    private Long id;
    private UserOutputDto user;
    private ArticleOutputDto article;
    private CommentOutputDto comment;

}
