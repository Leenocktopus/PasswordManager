package com.leandoer.logic.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "user", indexes = {
        @Index(name = "unique_index_username", columnList = "username", unique = true)
})
@Getter
@Setter
@ToString(of = {"id", "username", "password"})
@NoArgsConstructor
@EqualsAndHashCode(of = {"username"})
public class User {
    @Id
    @GeneratedValue(generator = "pm_generator")
    private long id;

    @Column(name = "username", length = 30)
    @Size(min = 6, max = 30)
    private String username;

    @Column(name = "password", length = 64)
    @Size(min = 64, max = 64)
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    List<Password> passwords = new ArrayList<>();
}
