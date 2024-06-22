package com.uautso.sovs.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DashboardResponse {

    private Long users;
    private Long votes;
    private Long candidates;
    private Long elections;
}
