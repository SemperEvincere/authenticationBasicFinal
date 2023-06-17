package com.bbva.authentication.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(customBasicAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/user/**")
                        .hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/api/user/me")
                        .hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/api/user/create")
                        .permitAll()
                    .antMatchers(HttpMethod.POST, "/api/user/promotion")
                        .hasRole("ADMIN")
                .anyRequest()
                    .authenticated()
                .and()
                .csrf()
                    .disable();
    }
}
