package com.dz.financial.reporting.service;

import com.dz.financial.reporting.repository.ApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantUserDetailsService implements UserDetailsService {

    private ApiUserRepository apiUserRepository;

    @Autowired
    public MerchantUserDetailsService(ApiUserRepository apiUserRepository) {
        this.apiUserRepository = apiUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return apiUserRepository.findOneByUsername(username)
                .map((u) -> new User(u.getUsername(), u.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
    }
}