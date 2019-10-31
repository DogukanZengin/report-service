package com.dz.financial.reporting.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dz.financial.reporting.exceptions.AuthenticationErrorResponse;
import com.dz.financial.reporting.model.db.enums.Status;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CredentialExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Autowired
    public CredentialExceptionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            sendErrorResponse(response, e);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, Exception e){

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(AuthenticationErrorResponse.buildErrorResponse(e)));
        } catch (IOException ex) {
            log.error("Error during AuthenticationErrorResponse JSON serialization", ex);
        }
    }
}