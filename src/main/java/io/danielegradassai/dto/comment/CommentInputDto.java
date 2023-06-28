package io.danielegradassai.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentInputDto {

    @NotBlank(message = "Il commento è vuoto")
    private String content;
    @NotNull(message = "L'ID User non può essere nullo!")
    private Long userId;
    @NotNull(message = "L'ID dell'articolo non può essere nullo")
    private Long articleId;
    @Null(message = "L'ID del commento genitore può essere nullo")
    private Long parentCommentId;
}
