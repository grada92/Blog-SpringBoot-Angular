package io.danielegradassai.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationnInputDto {

    private Long userId;
    private Long articleId;
    private Long commentId;
}
