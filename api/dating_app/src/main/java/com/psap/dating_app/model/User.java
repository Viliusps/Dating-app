package com.psap.dating_app.model;

import java.util.Date;

import com.psap.dating_app.model.enums.Gender;
import com.psap.dating_app.model.enums.MatchPurpose;
import com.psap.dating_app.model.enums.Role;
import com.psap.dating_app.model.enums.SearchGender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Phone is mandatory")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotBlank(message = "Role is mandatory")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank(message = "Birth date is mandatory")
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    
    @NotBlank(message = "Gender is mandatory")
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Height is mandatory")
    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "picture")
    private String picture;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "Search gender is mandatory")
    @Column(name = "search_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private SearchGender searchGender;

    @NotBlank(message = "Radius is mandatory")
    @Column(name = "radius", nullable = false)
    private Integer radius;

    @NotBlank(message = "Points is mandatory")
    @Column(name = "points", nullable = false)
    private Integer points;

    @NotBlank(message = "Block end is mandatory")
    @Column(name = "block_end", nullable = false)
    private Date blockEnd;

    @NotBlank(message = "Blocked is mandatory")
    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    @NotBlank(message = "Star sign is mandatory")
    @Column(name = "star_sign", nullable = false)
    private String starSign;

    @NotBlank(message = "Personality type is mandatory")
    @Column(name = "personality_type", nullable = false)
    private String personalityType;

    @NotBlank(message = "Love language is mandatory")
    @Column(name = "love_language", nullable = false)
    private String loveLanguage;

    @NotBlank(message = "Match purpose is mandatory")
    @Column(name = "match_purpose", nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchPurpose matchPurpose;
}
