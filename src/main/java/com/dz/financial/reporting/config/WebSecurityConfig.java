package com.dz.financial.reporting.config;

import com.dz.financial.reporting.exceptions.CredentialExceptionFilter;
import com.dz.financial.reporting.util.AppAuthenticationEntryPoint;
import com.dz.financial.reporting.util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtRequestFilter jwtRequestFilter;
    private CredentialExceptionFilter credentialExceptionFilter;
    private UserDetailsService merchantUserDetailsService;
    private AppAuthenticationEntryPoint appAuthenticationEntryPoint;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter,
                             CredentialExceptionFilter credentialExceptionFilter,
                             UserDetailsService merchantUserDetailsService,
                             AppAuthenticationEntryPoint appAuthenticationEntryPoint,
                             PasswordEncoder passwordEncoder) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.credentialExceptionFilter = credentialExceptionFilter;
        this.merchantUserDetailsService = merchantUserDetailsService;
        this.appAuthenticationEntryPoint = appAuthenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(merchantUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/webjars/**", "/swagger-ui.html","/merchant/user/login").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(appAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(credentialExceptionFilter, JwtRequestFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}