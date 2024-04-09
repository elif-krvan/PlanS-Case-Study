package com.plans.core.security;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
            User appUser = accountRepository.findUserByEmail(username).orElse(null); // TODO orelse throws 
        
            if (appUser != null) {
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    username,
                    appUser.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("USER")) //TODO update this
                );
                return userDetails;
            } else {
                throw new UsernameNotFoundException("User not found with email: " + username);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while loading user details.", e);
        }
    }
    
    public UserDetails loadUserByUsername(User appUser) throws UsernameNotFoundException {
        try {
            System.out.println("aaaaaaaaaaaa in user detail2");
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                                        appUser.getEmail(), 
                                        appUser.getPassword()
                                        // Collections.singleton(new SimpleGrantedAuthority(appUser.getUserType().toString()))
                                        , null
                                        );
            return userDetails; 
        } catch (Exception e) {
            throw e;
        }
    }    
}
