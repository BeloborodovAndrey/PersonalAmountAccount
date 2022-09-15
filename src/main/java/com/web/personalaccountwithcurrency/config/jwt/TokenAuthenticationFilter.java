package com.web.personalaccountwithcurrency.config.jwt;

import com.web.personalaccountwithcurrency.config.CustomAuthenticationManager;
import com.web.personalaccountwithcurrency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;


@Slf4j
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    private final CustomAuthenticationManager authenticationManager;

    public TokenAuthenticationFilter(String url, JwtProvider jwtProvider, UserService userService, CustomAuthenticationManager authenticationManager) {
        super(url);
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        setAuthenticationSuccessHandler((request, response, authentication) ->
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getRequestDispatcher(request.getServletPath() + request.getPathInfo()).forward(request, response);
        });
        setAuthenticationFailureHandler((request, response, authenticationException) ->
                response.getOutputStream().print(authenticationException.getMessage()));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("authenticate filter");
        String token = getTokenFromRequest(request);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            UserDetails userDetails = userService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            return authenticationManager.authenticate(auth);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(null, null, null));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,FilterChain chain, Authentication authentication) throws IOException {
        response.setContentType("application/json");
        response.sendRedirect("/homePage");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed) {
        log.error("error");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
            super.doFilter(req, res, chain);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (hasText(token)) {
            return token;
        }
        return null;
    }
}
