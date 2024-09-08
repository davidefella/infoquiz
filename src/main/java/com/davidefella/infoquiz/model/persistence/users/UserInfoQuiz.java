package com.davidefella.infoquiz.model.persistence.users;

import com.davidefella.infoquiz.model.persistence.users.role.InfoQuizRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserInfoQuiz implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    private String email;

    private String password;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InfoQuizRole infoQuizRole;

    // Costruttori
    protected UserInfoQuiz(String lastName, String firstName, String email, String password, InfoQuizRole infoQuizRole) {
        this.uuid = UUID.randomUUID();
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.creationDateTime = LocalDateTime.now();
        this.infoQuizRole = infoQuizRole;
    }

    protected UserInfoQuiz(UUID uuid, String lastName, String firstName, String email, String password, InfoQuizRole infoQuizRole) {
        this.uuid = uuid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.creationDateTime = LocalDateTime.now();
        this.infoQuizRole = infoQuizRole;
    }
    // Implementazione di UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(infoQuizRole.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO: Implement custom logic for account expiration
        return true; // Stub
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO: Implement custom logic for account locking
        return true; // Stub
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO: Implement custom logic for credential expiration
        return true; // Stub
    }

    @Override
    public boolean isEnabled() {
        // TODO: Implement custom logic for account enablement
        return true; // Stub
    }
}