package com.crudapp.demoapi.config;

import com.crudapp.demoapi.handler.CustomAuthEntryPoint;
import com.crudapp.demoapi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthEntryPoint customAuthEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.headers(header -> header.frameOptions(frameOptions -> frameOptions.sameOrigin())).authorizeHttpRequests(auth ->
                        auth.requestMatchers("/h2-console/**", "/api/register").permitAll().
                                requestMatchers("/api/**").hasRole("USER").anyRequest().hasAnyRole("ANONYMOUS")
                ).csrf(AbstractHttpConfigurer::disable).
                httpBasic(Customizer.withDefaults()).
                formLogin(AbstractHttpConfigurer::disable).
                exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthEntryPoint))
                .logout(AbstractHttpConfigurer::disable).
                sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Previous method for testing auth by in-memory auth and default username and password
    /*@Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(User.withUsername("user").password("{noop}password").roles("USER").build());
    }*/


}
