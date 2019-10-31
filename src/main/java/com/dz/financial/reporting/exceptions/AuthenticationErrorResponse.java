package com.dz.financial.reporting.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.dz.financial.reporting.model.db.enums.Status;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationErrorResponse {
    private final Status status;
    private final String message;
    private Integer code;

    public static AuthenticationErrorResponse buildErrorResponse(Exception e){
        String message;
        if (e instanceof ExpiredJwtException) {
            message = "Token Expired";
        } else if (e instanceof IllegalArgumentException) {
            message = "Token Required";
        } else {
            message = "Error during authentication.";
        }
        return new AuthenticationErrorResponse(Status.DECLINED, message, 0);
    }
}
