package com.kedop.missingperson.entities;

import com.kedop.missingperson.models.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = ColumnNames.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ColumnNames.FIRST_NAME)
    private String firstName;

    @Column(name = ColumnNames.LAST_NAME)
    private String lastName;

    @Column(name = ColumnNames.EMAIL)
    private String email;

    @Column(name = ColumnNames.USERNAME)
    private String username;

    @Column(name = ColumnNames.PASSWORD)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.ROLE)
    private Role role;

    @Column(name = ColumnNames.ENABLED)
    private Boolean enabled;

    @Column(name = ColumnNames.EXPIRED)
    private Boolean expired;

    @Column(name = ColumnNames.LOCKED)
    private Boolean locked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return expired != null && !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked != null && !locked ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled != null && enabled;
    }


    public static class ColumnNames {

        public static final String ID = "id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String ROLE = "role";
        public static final String EXPIRED = "expired";
        public static final String ENABLED = "enabled";
        public static final String LOCKED = "locked";
    }
}
