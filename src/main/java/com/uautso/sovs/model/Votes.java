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
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "votes")
@SQLDelete(sql = "UPDATE votes SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Votes extends BaseEntity implements Serializable {

    private LocalDateTime time = LocalDateTime.now();
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "candidate_id",nullable = false)
    private Candidates candidates;

    @ManyToOne
    private Election election;

}
