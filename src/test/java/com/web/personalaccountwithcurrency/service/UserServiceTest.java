package com.web.personalaccountwithcurrency.service;

import com.web.personalaccountwithcurrency.repository.entity.Role;
import com.web.personalaccountwithcurrency.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    void loadUserByUsername() {
        Assertions.assertNotNull(userService.loadUserByUsername("test"));
    }

    @Test
    void loadUserByUsernameAndPassword() {
        Assertions.assertNotNull(userService.loadUserByUsernameAndPassword("test", "test"));
    }

    @Test
    void saveUser() {
        Assertions.assertTrue(userService.saveUser("test", "test"));
    }

    @Test
    void isAuthorizedUser() {
        Assertions.assertFalse(userService.isAuthorizedUser("someToken"));
    }

    @Test
    void getCurrentUser() {
        User user = getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                user.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList()));
        User actualUser = userService.getCurrentUser(authentication);
        Assertions.assertNotNull(actualUser);
    }

    @Test
    void getRates() {
        Map<String, String> map = userService.getRates();
        Assertions.assertEquals(4, map.size());
    }

    @Test
    void logout() {
        User user = getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                user.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userService.logout();
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private User getUser() {
        User user = new User();
        user.setRoles(Stream.of(
                new Role(1L, "ROLE_USER")
        ).collect(Collectors.toSet()));
        user.setAmount("324");
        user.setToken("fewf");
        user.setPassword("pass");
        user.setUsername("username");
        return user;
    }
}