package com.plans.core.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plans.core.request.QUser;
import com.plans.core.request.QUserLogin;
import com.plans.core.response.RLoginUser;
import com.plans.core.response.RUser;
import com.plans.core.response.Response;
import com.plans.core.service.AccountService;
import com.plans.core.consts.Header;
import com.plans.core.enums.Role;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("auth")
public class AccountController {
    private final AccountService accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "login")
    public ResponseEntity<Object> login(@Valid @RequestBody QUserLogin userInfo, HttpServletResponse servletResponse) throws Exception {
        RLoginUser loggedUser = accountService.login(userInfo);

        servletResponse.addCookie(loggedUser.getAccessToken());
        servletResponse.addCookie(loggedUser.getRefreshToken());

        return Response.create("Login is successful", HttpStatus.OK, loggedUser.getUser());      
    }

    // Only PlanS admins can register via this endpoint
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "register") 
    public ResponseEntity<Object> register(@Valid @RequestBody QUser userInfo) throws Exception {
        RUser user = new RUser(accountService.register(userInfo, Role.ADMIN));
        return Response.create("Account is created", HttpStatus.OK, user);
    }

    @GetMapping("refresh")
    public ResponseEntity<Object> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth, 
                                                HttpServletResponse servletResponse) throws Exception {
        Cookie accessCookie = accountService.refreshToken(auth);
        servletResponse.addCookie(accessCookie);

        return Response.create("New access token is created", HttpStatus.OK);      
    }

    @GetMapping("test")
    public ResponseEntity<Object> testAuth(@RequestHeader(Header.USER_EMAIL) String email) {
        String test = "Welcome to the authenticated endpoint!";
        return Response.create("OK", HttpStatus.OK, test);  
    }
    
    @GetMapping("test1")
    public ResponseEntity<Object> testAuth1() {
        String test = "Welcome to the authenticated endpoint!";
        return Response.create("OK", HttpStatus.OK, test);  
    }

    // @GetMapping("/")
    // public ResponseEntity<Object> getUsers() {
    //     List<User> userList = accountService.getUsers();
    //     return Response.create("Ok", HttpStatus.OK, userList);        
    // }

    @GetMapping("hello")
    public ResponseEntity<Object> testUnsecuredEndpoint() {
        return Response.create("Ok", HttpStatus.OK);
    }
}
