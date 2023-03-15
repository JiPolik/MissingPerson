package com.kedop.missingperson.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kedop.missingperson.entities.User.ColumnNames;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oleksandrpolishchuk on 20.02.2023
 * @project MissingPerson
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @JsonProperty(value = JsonKeys.ID)
    private Long id;

    @JsonProperty(value = JsonKeys.FIRST_NAME)
    private String firstName;

    @JsonProperty(value = JsonKeys.LAST_NAME)
    private String lastName;

    @JsonProperty(value = JsonKeys.EMAIL)
    private String email;

    @JsonProperty(value = JsonKeys.PASSWORD)
    private String password;

    @JsonProperty(value = JsonKeys.USERNAME)
    private String username;

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = JsonKeys.ROLE)
    private Role role;

    @JsonProperty(value = JsonKeys.ENABLED)
    private Boolean enabled;

    @JsonProperty(value = JsonKeys.EXPIRED)
    private Boolean expired;

    @JsonProperty(value = JsonKeys.LOCKED)
    private Boolean locked;

    public static class JsonKeys {

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
