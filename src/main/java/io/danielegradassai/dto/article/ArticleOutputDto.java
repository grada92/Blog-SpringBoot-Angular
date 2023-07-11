package io.danielegradassai.dto.article;

import io.danielegradassai.dto.comment.CommentOutputDto;
import io.danielegradassai.dto.user.UserOutputDto;
import io.danielegradassai.dto.vote.VoteOutputDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleOutputDto {

    private Long id;
    private String title;
    private String content;
    private UserOutputDto user;
    private boolean isApproved;
    private LocalDateTime createdAt;
    private List<CategoryDto> categories;
    private List<TagDto> tags;
    private List<CommentOutputDto> comments;
    private List<VoteOutputDto> votes;
    private int likeCount;
    private int dislikeCount;

}
