package com.marvic.factsigner.payload.sistema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {

    @Size(max=100)
    private String name; // Cert. file name

    @Size(max=100)
    private String key; // Certificate Secret

    private String body; // Certificate

}
