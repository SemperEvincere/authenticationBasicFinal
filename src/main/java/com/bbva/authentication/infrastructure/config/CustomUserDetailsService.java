package com.bbva.authentication.infrastructure.config;

import com.bbva.authentication.application.useCases.IUserUseCase;
import com.bbva.authentication.infrastructure.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserUseCase userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return userService.findByUsername(username)
		                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        UserEntity user = userService.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRoles()
//                        .stream()
//                        .map(Enum::name)
//                        .toArray(String[]::new))
//                .build();

    }

}
