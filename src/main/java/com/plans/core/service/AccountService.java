package com.plans.core.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.plans.core.consts.Header;
import com.plans.core.exception.AlreadyFoundException;
import com.plans.core.exception.CustomException;
import com.plans.core.exception.LoginException;
import com.plans.core.exception.SomethingWentWrongException;
import com.plans.core.model.User;
import com.plans.core.repository.IAccountRepository;
import com.plans.core.request.QUser;
import com.plans.core.request.QUserLogin;
import com.plans.core.response.RLoginUser;
import com.plans.core.response.RRegisterUser;
import com.plans.core.security.JWTUserService;
import com.plans.core.security.JWTUtils;

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    public static int hashStrength = 10;

    @Autowired
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final IAccountRepository accountRepository;
    private final JWTUserService jwtUserService;

    @Autowired
    private final JWTUtils jwtUtils;


    public RLoginUser login(QUserLogin user) throws Exception {
        try {
            // If user does not exist, throw a login exception
            User dbUser = accountRepository.findUserByEmail(user.getEmail()).orElseThrow(() -> new LoginException());

            // Verify password
            String hashedPassword = dbUser.getPassword();
            boolean passwordMatch = bCryptPasswordEncoder.matches(user.getPassword(), hashedPassword);

            if (!passwordMatch) {
                throw new LoginException();
            }

            // Generate access and refresh tokens
            final UserDetails userDetails = jwtUserService.loadUserByUsername(user.getEmail());
            final String accessToken = jwtUtils.createAccessToken(userDetails);
            final String refreshToken = jwtUtils.createRefreshToken(userDetails);

            Cookie accessTokenCookie = new Cookie(Header.ACCESS_TOKEN, accessToken);
            accessTokenCookie.setHttpOnly(true);

            // Set refresh token as HTTP-only cookie
            Cookie refreshTokenCookie = new Cookie(Header.REFRESH_TOKEN, refreshToken);
            refreshTokenCookie.setHttpOnly(true);

            log.info("User {} logged in.", dbUser.getId());

            return new RLoginUser(dbUser, accessTokenCookie, refreshTokenCookie);
        } catch (CustomException e2) {
            log.error("User login failed due to wrong email or password for email {}", user.getEmail());

            throw e2;
        } catch (Exception e2) {
            log.error("User login failed for email {}", user.getEmail(), e2);
            throw new SomethingWentWrongException();
        }
    }

    public Cookie refreshToken(String auth) throws Exception {
        try {
            String username = jwtUtils.extractRefreshUsername(JWTUtils.getTokenWithoutBearer(auth));

            final UserDetails userDetails = jwtUserService.loadUserByUsername(username);
            final String accessToken = jwtUtils.createAccessToken(userDetails);

            Cookie accessTokenCookie = new Cookie(Header.ACCESS_TOKEN, accessToken);
            accessTokenCookie.setHttpOnly(true);

            return accessTokenCookie;
        } catch (Exception e) {
            log.error("Error in generating new access token with refresh token", e);
            throw new SomethingWentWrongException();
        }
    }

    @Transactional
    public RRegisterUser addUser(QUser requestUser) throws Exception {
        try {
            // If user with the same email or username exists, throw an exception
            boolean userExist = accountRepository.existsByEmail(requestUser.getEmail());
            
            if (userExist) {
                throw new AlreadyFoundException("User already exists with the provided email address. Please use a different email or sign in.");
            }

            userExist = accountRepository.existsByUsername(requestUser.getUsername());
            
            if (userExist) {
                throw new AlreadyFoundException("User already exists with the provided username. Please use a different username or sign in.");
            }

            // Generate uuid and hash the password if user does not exist in the system
            requestUser.setId(UUID.randomUUID());
            requestUser.setPassword(encodePassword(requestUser.getPassword()));

            User newUser = accountRepository.save(new User(requestUser));

            log.info("User registered with email {}.", newUser.getId());

            return new RRegisterUser(newUser);          
        } catch (AlreadyFoundException e) {
            log.error("User register fail since email already exists for email {}", requestUser.getEmail());
            throw e;
        } catch (Exception e) {
            log.error("User register failed for email {}", requestUser.getEmail(), e);
            throw new SomethingWentWrongException();
        }
    }

    public List<User> getUsers() {
        try {
            // return accountRepository.findAll().stream().map(user -> new RUser(user)).collect(Collectors.toList());
            return accountRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    private String encodePassword(String plainPassword) {
        try {
            return bCryptPasswordEncoder.encode(plainPassword);
        } catch (Exception e) {
            throw e;
        }
    }
}
