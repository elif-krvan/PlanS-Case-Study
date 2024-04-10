package com.plans.core.security;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.plans.core.exception.NotFoundException;
import com.plans.core.model.User;
import com.plans.core.repository.IAccountRepository;

import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JWTUserService implements UserDetailsService {
    private final IAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User appUser = accountRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User is not found!")); // TODO check with non existing email
        
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username,
                appUser.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().toString()))
            );
            return userDetails;

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while loading user details.", e);
        }
    }    
}
