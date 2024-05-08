package com.uautso.sovs.dto;


import com.uautso.sovs.utils.enums.ElectionCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ElectionDto {

    private String name;
    private Integer year;
    private String uuid;
    private String description;
    private ElectionCategory category;
}
