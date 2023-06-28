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
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = false)
    private boolean isApproved;
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
    @OneToOne
    @JoinColumn(name = "validation_rule_id")
    private ValidationAdmin titleValidationRule;

    public Article(String title, String content, User user, List<Category> categories, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.categories = categories;
        this.tags = tags;
    }

    public Article(String title, String content, User user, List<Category> categories, List<Tag> tags, ValidationAdmin titleValidationRule) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.categories = categories;
        this.tags = tags;
        this.titleValidationRule = titleValidationRule;
    }
}
