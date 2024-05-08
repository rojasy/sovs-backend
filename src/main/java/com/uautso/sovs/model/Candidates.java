package com.uautso.sovs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql = "UPDATE candidates SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Candidates extends BaseEntity implements Serializable {

    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "election_id",nullable = false)
    private Election election;

    @OneToMany(mappedBy = "candidates",cascade = CascadeType.ALL)
    private List<Votes> votes;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserAccount userAccount;
}
