package com.example.corebancario.strategy;

import com.example.corebancario.model.Cliente;

public interface IAprobacionStrategy {
    boolean esAprobado(Cliente cliente);
}
