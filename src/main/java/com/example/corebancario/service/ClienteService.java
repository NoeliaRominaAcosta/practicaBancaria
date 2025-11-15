package com.example.corebancario.service;

import com.example.corebancario.dto.ClienteDTO;
import com.example.corebancario.model.Cliente;

public interface ClienteService {
    Cliente createCliente(ClienteDTO clienteDTO);
}
