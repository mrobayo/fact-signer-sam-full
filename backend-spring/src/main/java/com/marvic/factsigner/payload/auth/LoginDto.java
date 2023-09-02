package com.marvic.factsigner.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank @Size(min = 3)
    private String username; // OrEmail

    @NotBlank @Size(min = 8)
    private String password;

    @Size(min = 13, max = 13)
    private String empresaId;
}
