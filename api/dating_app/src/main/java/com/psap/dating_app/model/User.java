package com.psap.dating_app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psap.dating_app.model.enums.Gender;
import com.psap.dating_app.model.enums.LoveLanguages;
import com.psap.dating_app.model.enums.MatchPurpose;
import com.psap.dating_app.model.enums.PersonalityType;
import com.psap.dating_app.model.enums.Role;
import com.psap.dating_app.model.enums.SearchGender;
import com.psap.dating_app.model.enums.StarSign;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Role is mandatory")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull(message = "Birth date is mandatory")
    @Column(name = "birth_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date birthDate;
    
    @NotNull(message = "Gender is mandatory")
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Height is mandatory")
    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "picture")
    private String picture;

    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Search gender is mandatory")
    @Column(name = "search_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private SearchGender searchGender;

    @NotNull(message = "Radius is mandatory")
    @Column(name = "radius", nullable = false)
    private Integer radius;

    @NotNull(message = "Points is mandatory")
    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "block_end", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date blockEnd;

    @NotNull(message = "Blocked is mandatory")
    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    @Column(name = "star_sign", nullable = false)
    @Enumerated(EnumType.STRING)
    private StarSign starSign;

    @Column(name = "personality_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonalityType personalityType;

    @Column(name = "love_language", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoveLanguages loveLanguage;

    @NotNull(message = "Match purpose is mandatory")
    @Column(name = "match_purpose", nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchPurpose matchPurpose;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private List<Song> songs = new ArrayList<>();
}
