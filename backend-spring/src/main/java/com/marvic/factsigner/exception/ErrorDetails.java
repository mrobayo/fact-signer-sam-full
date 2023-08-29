package com.marvic.factsigner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {

    private int status;
    private String path;
    private String timestamp;
    private String message;
    private String details;

}
