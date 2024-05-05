package com.uautso.sovs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "candidates")
public class Candidates extends BaseEntity implements Serializable {

    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @OneToMany(mappedBy = "candidates",cascade = CascadeType.ALL)
    private List<Votes> votes;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserAccount userAccount;
}
