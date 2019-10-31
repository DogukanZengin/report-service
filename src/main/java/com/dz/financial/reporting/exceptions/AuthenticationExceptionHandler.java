package com.dz.financial.reporting.exceptions;

import com.dz.financial.reporting.model.db.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<AuthenticationErrorResponse> handleAuthenticationErrors(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        AuthenticationErrorResponse aer = new AuthenticationErrorResponse(Status.DECLINED,
                "Merchant User credentials are not valid", 0);

        return new ResponseEntity<>(aer, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
