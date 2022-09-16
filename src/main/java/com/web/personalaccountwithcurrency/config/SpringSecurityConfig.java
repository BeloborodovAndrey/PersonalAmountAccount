package com.web.personalaccountwithcurrency.config;

import com.web.personalaccountwithcurrency.config.handler.SecurityContextLogoutHandler;
import com.web.personalaccountwithcurrency.config.jwt.JwtProvider;
import com.web.personalaccountwithcurrency.config.jwt.TokenAuthenticationFilter;
import com.web.personalaccountwithcurrency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserService userService;

    private final JwtProvider jwtProvider;

    private final CustomAuthenticationManager authenticationManager;

    private final SecurityContextLogoutHandler securityContextLogoutHandler;


    public SpringSecurityConfig(@Lazy UserService userService, JwtProvider jwtProvider, CustomAuthenticationManager authenticationManager, SecurityContextLogoutHandler securityContextLogoutHandler) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.securityContextLogoutHandler = securityContextLogoutHandler;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/home", "/account*").access("hasRole('USER')")
                .antMatchers("/homePage").hasRole("USER")
                .antMatchers("/resources/**").permitAll()
                .and().csrf().disable().formLogin().loginPage("/index").permitAll()
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/homePage")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/userLogout"))
                .logoutSuccessHandler(securityContextLogoutHandler)
                .and()
                .addFilterAfter(new TokenAuthenticationFilter("/home", jwtProvider, userService, authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
