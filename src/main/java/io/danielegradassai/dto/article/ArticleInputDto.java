package io.danielegradassai.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInputDto {

    private Long userId;
    @NotBlank(message = "Titolo è richiesto")
    @Size(max = 100, message = "Titolo non può essere superiore a 100 caratteri")
    private String title;
    @NotBlank(message = "Richiesto contenuto")
    private String content;
    @NotNull(message = "Categoria non può essere nulla")
    private List<Long> categories;
    @NotNull(message = "Tag non può essere nullo")
    private List<Long> tags;

}
