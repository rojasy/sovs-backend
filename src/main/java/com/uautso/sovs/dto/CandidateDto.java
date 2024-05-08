package com.uautso.sovs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    private String uuid;
    private String title;
    private String description;
    private String userUuid;
    private String electionUuid;
}
