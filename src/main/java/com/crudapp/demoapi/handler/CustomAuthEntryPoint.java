package com.crudapp.demoapi.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Authentication failed: ", authException);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set status 401
        response.setContentType("application/json"); // Ensure the response is in JSON format

        // Send custom error message
        if (authException instanceof BadCredentialsException) {
            response.getWriter().write("Invalid username/password");
        } else if (authException instanceof InsufficientAuthenticationException){
            response.getWriter().write("Sorry,You don't have required permissions");
        }
        response.getWriter().flush();
    }


}
