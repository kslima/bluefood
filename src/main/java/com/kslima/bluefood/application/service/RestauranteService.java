package com.kslima.bluefood.application.service;

import com.kslima.bluefood.domain.cliente.Cliente;
import com.kslima.bluefood.domain.cliente.ClienteRepository;
import com.kslima.bluefood.domain.restaurante.Restaurante;
import com.kslima.bluefood.domain.restaurante.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void saveRestaurante(Restaurante restaurante) throws ValidationException {
        validateEmail(restaurante.getEmail(), restaurante.getId());
        if (restaurante.getId() != null) {

            Restaurante restauranteDb = restauranteRepository.findById(restaurante.getId())
                    .orElseThrow(NoSuchElementException::new);

            restaurante.setSenha(restauranteDb.getSenha());

        }else {
            restaurante.encryptPassword();
            restaurante = restauranteRepository.save(restaurante);
            restaurante.setLogotipoFileName();
            imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
        }

    }

    private void validateEmail(String email, Integer id) throws ValidationException {
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente != null) {
            throw new ValidationException("Email inválido ou já cadastrado");
        }

        Restaurante restaurante = restauranteRepository.findByEmail(email);

        if (restaurante != null) {
            // se o e-mail já exister, esse código verifica se é e-mail do própio cliente.
            if (!restaurante.getId().equals(id)) {
                throw new ValidationException("Email inválido ou já cadastrado");
            }

        }


    }

}