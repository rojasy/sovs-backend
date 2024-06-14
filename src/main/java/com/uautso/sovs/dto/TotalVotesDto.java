package com.uautso.sovs.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TotalVotesDto {

    private String candidateUuid;
    private String electionUuid;;
}
