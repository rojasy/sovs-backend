package com.uautso.sovs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "votes")
public class Votes extends BaseEntity implements Serializable {

    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "candidate_id",nullable = false)
    private Candidates candidates;
}
