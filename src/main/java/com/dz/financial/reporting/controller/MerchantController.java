package com.dz.financial.reporting.controller;

import com.dz.financial.reporting.util.JwtTokenUtil;
import com.dz.financial.reporting.model.db.enums.Status;
import com.dz.financial.reporting.model.rest.JwtRequest;
import com.dz.financial.reporting.model.rest.JwtResponse;
import com.dz.financial.reporting.service.MerchantUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class MerchantController {

    private JwtTokenUtil jwtTokenUtil;
    private MerchantUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public MerchantController(JwtTokenUtil jwtTokenUtil, MerchantUserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/merchant/user/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(Status.APPROVED, token));
    }
}