package io.danielegradassai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @Column(name = "article_id", nullable = false)
    private Article article;

    public Comment(String content, User user, Article article) {
        this.content = content;
        this.user = user;
        this.article = article;
    }
}
