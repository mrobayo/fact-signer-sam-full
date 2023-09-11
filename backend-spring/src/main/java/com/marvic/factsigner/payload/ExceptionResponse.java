/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvic.factsigner.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

  private Integer status;
  private String path;
  private String errorMessage;
  private String timeStamp;
  private String trace;

  public ExceptionResponse(int status, Map<String, Object> errorAttributes) {
    this.setStatus(status);
    this.setPath((String) errorAttributes.get("path"));
    this.setErrorMessage((String) errorAttributes.get("error"));
    this.setTimeStamp(errorAttributes.get("timestamp").toString());
    this.setTrace((String) errorAttributes.get("trace"));
  }

}
