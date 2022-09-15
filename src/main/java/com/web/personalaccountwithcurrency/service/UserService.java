package com.web.personalaccountwithcurrency.service;


import com.web.personalaccountwithcurrency.config.jwt.JwtProvider;
import com.web.personalaccountwithcurrency.repository.RatesRepository;
import com.web.personalaccountwithcurrency.repository.UserRepository;
import com.web.personalaccountwithcurrency.repository.entity.Rates;
import com.web.personalaccountwithcurrency.repository.entity.Role;
import com.web.personalaccountwithcurrency.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * main service for all user operations
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final RatesRepository ratesRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtProvider jwtProvider, RatesRepository ratesRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtProvider = jwtProvider;
        this.ratesRepository = ratesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User loadUserByUsernameAndPassword(String login, String password) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(login);

            if (user == null || !checkPassword(password, user.getPassword())) {
                throw new UsernameNotFoundException("User not found");
            } else {
                log.info("find user: {}", user);
                return user;
            }

        } catch (UsernameNotFoundException ex) {
            log.debug("Can't find user");
            return new User();
        }
    }

    public boolean saveUser(String login, String password) {
        User userFromDB = userRepository.findByUsername(login);
        if (userFromDB == null) {
            User user = buildUser(login, password);
            try {
                userRepository.save(user);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        } else {
            log.info("user with this name already registered: {}", userFromDB);
            return false;
        }
    }

    public boolean isAuthorizedUser(String token) {
        User user = userRepository.findByToken(token);
        if (user == null) {
            return false;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String authUser = String.valueOf(auth.getPrincipal());
            return authUser.equals(user.getUsername());
        }
        return false;
    }

    private User buildUser(String login, String password) {
        String pass = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setUsername(login);
        user.setPassword(pass);
        user.setToken(jwtProvider.generateToken(login));
        user.setAmount(String.valueOf(new Random().nextInt(100000)));
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        return user;
    }

    public User getCurrentUser(Authentication authentication) {
        String userName = String.valueOf(authentication.getPrincipal());
        return (User) loadUserByUsername(userName);
    }

    public Map<String, String> getRates() {
        return ratesRepository.findAll().stream().collect(Collectors.toMap(
                Rates::getCurrency,
                item -> String.valueOf(item.getAmount())
        ));
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    private boolean checkPassword(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }

}
