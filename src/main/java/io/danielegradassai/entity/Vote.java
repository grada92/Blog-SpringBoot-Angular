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
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean liked;
    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @Column(name = "article_id", nullable = false)
    private Article article;

    public Vote(boolean liked, User user, Article article) {
        this.liked = liked;
        this.user = user;
        this.article = article;
    }
}
