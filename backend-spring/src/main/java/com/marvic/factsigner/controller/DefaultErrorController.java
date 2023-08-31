package com.marvic.factsigner.controller;

import com.marvic.factsigner.exception.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//TODO @RestController
public class DefaultErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ErrorDetails handleError(WebRequest request, HttpServletResponse response) {
        Map<String, Object> details = getErrorAttributes(request);

        ErrorDetails error = new ErrorDetails();
        error.setStatus(response.getStatus());
        error.setPath((String) details.get("path"));
        error.setMessage((String) details.get("message"));
        error.setTimestamp(details.get("timestamp").toString());

        return error;
    }

    private Map<String, Object> getErrorAttributes(WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.putAll(this.errorAttributes.getErrorAttributes(request, null));
        return map;
    }

}
