package com.leandoer.logic.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "Password")
@Table(name = "password", indexes = {
        @Index(name = "unique_index_username_url", columnList = "username, resource_url, user_id", unique = true)
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Password {
    @Id
    @GeneratedValue(generator = "pm_generator")
    @Column(name = "id")
    private long id;

    @EqualsAndHashCode.Include
    @Column(name = "username", length = 30)
    @Size(max = 30)
    private String username;

    @Column(name = "password", length = 30)
    @Size(max = 30)
    private String password;

    @EqualsAndHashCode.Include
    @Column(name = "resource_url")
    @Size(max = 255)
    private String resourceUrl;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
