package io.danielegradassai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Vote> votes = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(String username, String password, List<Role> ruoli) {
        this.username = username;
        this.password = password;
        this.roles = ruoli;
    }
}
