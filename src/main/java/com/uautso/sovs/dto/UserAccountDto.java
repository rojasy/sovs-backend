package com.uautso.sovs.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {

    private String uuid;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String institutionUuid;

}
