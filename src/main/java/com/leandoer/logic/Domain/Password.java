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
@ToString(of = {"id","username", "password", "description", "resourceUrl"})
@NoArgsConstructor
@EqualsAndHashCode(of = {"username", "resourceUrl", "user"})
public class Password {
    @Id
    @GeneratedValue(generator = "pm_generator")
    @Column(name = "id")
    private long id;

    @Column(name = "username", length = 30)
    @Size(min = 6, max = 30)
    private String username;

    @Column(name = "password", length = 120)
    @Size(min = 6, max = 120)
    private String password;

    @Column(name = "resource_url")
    @Size(max = 255)
    private String resourceUrl;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
