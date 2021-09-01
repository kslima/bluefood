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
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Transactional
    public void saveCliente(Cliente cliente) throws ValidationException{
        validateEmail(cliente.getEmail(), cliente.getId());

        if (cliente.getId() != null) {
            Cliente clienteDb = clienteRepository.findById(cliente.getId()).orElseThrow(NoSuchElementException::new);
            cliente.setSenha(clienteDb.getSenha());

        } else {
            cliente.encryptPassword();
        }
        clienteRepository.save(cliente);
    }

    private void validateEmail(String email, Integer id) throws ValidationException {
        Restaurante restaurante = restauranteRepository.findByEmail(email);

        if (restaurante != null) {
            throw new ValidationException("Email inválido ou já cadastrado");
        }

        Cliente cliente = clienteRepository.findByEmail(email);
         
        if (cliente != null) {
              
            // se o e-mail já exister, esse código verifica se é e-mail do própio cliente.
            if (!cliente.getId().equals(id)) {
                throw new ValidationException("Email inválido ou já cadastrado");
            }

        }

    }

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

}
