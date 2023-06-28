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
public class ValidationAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int maxTitleLength;
    @Column(nullable = false)
    private int maxContentLength;

    public ValidationAdmin(int maxTitleLength, int maxContentLength) {
        this.maxTitleLength = maxTitleLength;
        this.maxContentLength = maxContentLength;
    }
}
