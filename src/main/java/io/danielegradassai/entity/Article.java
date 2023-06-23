package io.danielegradassai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Lob
    @Column(nullable = true)
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;
    @ManyToMany
    private List<Category> categories;
    @ManyToMany
    private List<Tag> tags;
    @OneToMany(mappedBy = "article")
    private List<Vote> votes;

    public Article(String title, String content, User user, List<Category> categories, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.categories = categories;
        this.tags = tags;
    }
}
