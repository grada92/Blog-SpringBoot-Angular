package io.danielegradassai.dto.vote;

import io.danielegradassai.dto.article.ArticleOutputDto;
import io.danielegradassai.dto.user.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteOutputDto {

    private Long id;
    private boolean liked;
    private boolean disliked;
    private Long userId;
    private Long articleId;
}
