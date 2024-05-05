package com.uautso.sovs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.leangen.graphql.annotations.GraphQLIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_accounts")
@SQLDelete(sql = "UPDATE user_accounts SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class UserAccount extends BaseEntity implements Serializable {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;



    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name = "username", unique = true)
    private String username;

    @Email
    @Column(name = "Email", unique = true)
    private String email;

    @Pattern(regexp = "(^(([2]{1}[5]{2})|([0]{1}))[1-9]{2}[0-9]{7}$)", message = "Please enter valid phone number eg. 255766040293")
    @Column(name = "phone_number", unique = true)
    private String phone;

    private String password;



    @JoinTable(name = "role_user", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "userAccount",cascade = CascadeType.ALL)
    private List<Votes> votes;



    @JsonIgnore
    @GraphQLIgnore
    @Basic(optional = true)
    @Column(name = "token_created_at")
    private LocalDateTime tokenCreatedAt = LocalDateTime.now();

    @GraphQLIgnore
    @JsonIgnore
    @Basic(optional = true)
    @Column(name = "last_login")
    private LocalDateTime lastLogin;


    @Basic(optional = true)
    @Column(name = "reset_link_sent")
    private Boolean resetLinkSent = false;

    @Basic(optional = true)
    @Column(name = "remember_token")
    private String rememberToken;

    @JsonIgnore
    @GraphQLIgnore
    private int loginAttempts = 0;

    @GraphQLIgnore
    @JsonIgnore
    private LocalDateTime lastLoginAttempt;

    @JsonIgnore
    @GraphQLIgnore
    @Column(name = "refresh_token")
    private String refreshToken;

    @JsonIgnore
    @GraphQLIgnore
    @Column(name = "refresh_token_created_at")
    private LocalDateTime refreshTokenCreatedAt;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public String getFullName() {
        return this.firstName + (middleName != null ? " "+this.middleName+" " : " ") +this.lastName;
    }
}
