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
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(generator = "pm_generator")
    private long id;

    @EqualsAndHashCode.Include
    @Column(name = "username", length = 30)
    @Size(max = 30)
    private String username;

    @Column(name = "password", length = 30)
    @Size(max = 30)
    private String password;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Password> passwords = new ArrayList<>();
}
