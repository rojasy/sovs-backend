package com.uautso.sovs.model;

import com.uautso.sovs.utils.enums.ElectionCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "election")
@SQLDelete(sql = "UPDATE election SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Election extends BaseEntity implements Serializable {

    private String name;

    private String description;

    @Column(name = "year")
    private Integer year;

//    @Enumerated(EnumType.STRING)
    @Column(name = "election_category")
    private ElectionCategory category;

    @OneToMany
    private List<Votes> votes = new ArrayList<>();
}
