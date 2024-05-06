package com.uautso.sovs.model;

import com.uautso.sovs.utils.enums.ElectionCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "election")
public class Election extends BaseEntity implements Serializable {

    private String name;

    private String description;

    @Column(name = "year")
    private Integer year;

    @Column(name = "election_category")
    private ElectionCategory category;

    @OneToMany
    private List<Votes> votes = new ArrayList<>();
}
