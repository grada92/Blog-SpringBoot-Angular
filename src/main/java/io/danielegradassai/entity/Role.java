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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String authority;
    @ManyToMany(mappedBy = "ruoli")
    private List<User> users = new ArrayList<>();
    public Role(String authority) { this.authority = authority;}
    @Override
    public String toString() {
        return authority;
    }

}
