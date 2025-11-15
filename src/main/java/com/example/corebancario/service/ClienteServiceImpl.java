package com.example.corebancario.service;

import com.example.corebancario.dto.ClienteDTO;
import com.example.corebancario.model.Cliente;
import com.example.corebancario.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente.Builder()
                .nombre(clienteDTO.nombre())
                .email(clienteDTO.email())
                .scoreCrediticio(clienteDTO.scoreCrediticio())
                .build();
        return clienteRepository.save(cliente);
    }
}
