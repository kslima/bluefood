package com.kslima.bluefood.infrastructure.security;

import com.kslima.bluefood.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Role role = SecurityUtils.loggedUser().getRole();

        if (role == Role.CLIENTE) {
            response.sendRedirect("cliente/home");

        } else if (role == Role.RESTAURANTE) {
            response.sendRedirect("restaurante/home");

        } else {
            throw new IllegalArgumentException("Erro na autenticação");
        }
    }
}
