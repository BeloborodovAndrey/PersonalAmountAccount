package com.web.personalaccountwithcurrency.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Performs a logout
 */
@Slf4j
@Component
public class SecurityContextLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null &&
                !authentication.getPrincipal().equals("AnonymousUser") &&
                authentication.isAuthenticated()) {
            try {
                request.getSession().invalidate();
                log.info("User Successfully Logout");
                SecurityContextHolder.getContext().setAuthentication(null);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/login");
    }
}
