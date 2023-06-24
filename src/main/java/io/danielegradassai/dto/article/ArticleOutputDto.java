package io.danielegradassai.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleOutputDto {

    private Long id;
    private String title;
    private String content;
    private byte[] image;
    private Long userId;
    private List<CategoryDto> categories;
    private List<TagDto> tags;

}
