package com.plans.core.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.plans.core.exception.UnauthorizedException;
import com.plans.core.response.Response;
import com.plans.core.consts.Header;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final static String tokenBearer = "Bearer";

    private final JWTUserService jwtUserService;
    private final JWTUtils jwtUtils;

    public static String getTokenWithoutBearer(String token) {
        return token.substring(tokenBearer.length());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String currentEndpoint = request.getServletPath();
        boolean isWhitelisted = Arrays.stream(SecurityConfig.AUTH_WHITELIST)
                                        .anyMatch(path -> path.equals(currentEndpoint));
    
        if (isWhitelisted) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Incoming request to {} ", request.getServletPath());

        String username = "";
        String token = "";
        

        try {
            if (tokenHeader == null || !tokenHeader.startsWith(tokenBearer)) {
                throw new UnauthorizedException();
            }

            token = getTokenWithoutBearer(tokenHeader);            

            boolean isCurrentRefresh = currentEndpoint.equals("/api/v1/auth/refresh");

            // If the token in the header is refresh token and it is expired, send error message
            if (isCurrentRefresh && jwtUtils.isRefreshTokenExpired(token)) {
                throw new UnauthorizedException();
            }

            // If the token in the header is access token and it is expired, send error message
            if (!isCurrentRefresh && jwtUtils.isAccessTokenExpired(token)) {
                throw new UnauthorizedException();
            }

            // Extract the token based on its type
            if (isCurrentRefresh) {
                username = jwtUtils.extractRefreshUsername(token);
            } else {
                username = jwtUtils.extractAccessUsername(token);
            }

            final String USERNAME = username;

            // if username cannotbe extracted from the token, send error message
            if (USERNAME == null) {
                throw new UnauthorizedException();
            }

            UserDetails user = jwtUserService.loadUserByUsername(USERNAME);

            if ((isCurrentRefresh && jwtUtils.validateRefreshToken(token, user)) || jwtUtils.validateAccessToken(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } 

            if (!isCurrentRefresh) {
                // Remove the Authorization header
                // request.setH(HttpHeaders.AUTHORIZATION);
            }

            CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request);
            requestWrapper.addHeader(Header.USER_EMAIL, username);

            // Pass the wrapped request to the next filter or servlet in the chain
            filterChain.doFilter(requestWrapper, response);
        } catch (UnauthorizedException e) {
            Response.createServletError(e, response);
            return;
        } catch (JwtException | IllegalArgumentException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            Response.createServletError("Login is unsuccessful", status, response);
            return;
        }       
    }

   
}
