package com.kslima.bluefood.infrastructure.security;

import com.kslima.bluefood.domain.Usuario;
import com.kslima.bluefood.domain.cliente.ClienteRepository;
import com.kslima.bluefood.domain.restaurante.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = clienteRepository.findByEmail(username);

        if (usuario == null) {
            usuario = restauranteRepository.findByEmail(username);

            if (usuario == null) {
                throw new UsernameNotFoundException(username);
            }
        }
        return new LoggedUser(usuario);
    }
}
