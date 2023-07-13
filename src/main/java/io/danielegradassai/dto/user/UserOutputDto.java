package io.danielegradassai.dto.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserOutputDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;
    private boolean subscription;

}
