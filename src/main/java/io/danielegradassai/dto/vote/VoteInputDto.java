package io.danielegradassai.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteInputDto {

    private boolean liked;
    private boolean disliked;
    private Long userId;
    private Long articleId;
}
