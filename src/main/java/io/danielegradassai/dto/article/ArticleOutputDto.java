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

    @NotNull(message = "L'ID dell'utente non può essere nullo")
    private Long userId;
    @NotBlank(message = "Il titolo dell'articolo non può essere vuoto")
    @Size(max = 100, message = "Il titolo dell'articolo può contenere al massimo 100 caratteri")
    private String title;
    @NotBlank(message = "Il contenuto dell'articolo non può essere vuoto")
    private String content;
    private String imageUrl;
    @NotNull(message = "La lista delle categorie non può essere nulla")
    private List<CategoryDto> categories;
    @NotNull(message = "La lista dei tag non può essere nulla")
    private List<TagDto> tags;

}
