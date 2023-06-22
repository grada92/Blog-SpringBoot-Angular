package io.danielegradassai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String authority;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
    public Role(String authority) { this.authority = authority;}
    @Override
    public String toString() {
        return authority;
    }

}
