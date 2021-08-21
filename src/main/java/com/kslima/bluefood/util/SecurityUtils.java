package com.kslima.bluefood.util;

import com.kslima.bluefood.domain.cliente.Cliente;
import com.kslima.bluefood.domain.restaurante.Restaurante;
import com.kslima.bluefood.infrastructure.security.LoggedUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static LoggedUser loggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (LoggedUser) authentication.getPrincipal();
    }

    public static Cliente loggetdCliente() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            throw new IllegalArgumentException("Não existe um usuário logado");
        }

        if (!(loggedUser.getUsuario() instanceof Cliente)) {
            throw new IllegalArgumentException("O usuário logado não é um cliente");
        }

        return (Cliente) loggedUser.getUsuario();
    }

    public static Restaurante loggetdRestaurante() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            throw new IllegalArgumentException("Não existe um usuário logado");
        }

        if (!(loggedUser.getUsuario() instanceof Restaurante)) {
            throw new IllegalArgumentException("O usuário logado não é um restaurante");
        }

        return (Restaurante) loggedUser.getUsuario();
    }
}
