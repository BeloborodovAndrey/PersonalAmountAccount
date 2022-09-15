package com.web.personalaccountwithcurrency.config;

import com.web.personalaccountwithcurrency.repository.entity.Role;
import com.web.personalaccountwithcurrency.repository.entity.User;
import com.web.personalaccountwithcurrency.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserService userService;

    public CustomAuthenticationManager(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        User authUser = (User) authentication.getPrincipal();
        String username = authUser.getUsername();

        UserDetails user = userService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("1000");
        }
        User dbUser = (User) user;
        Set<Role> userRights = dbUser.getRoles();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, userRights.stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList())));
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
