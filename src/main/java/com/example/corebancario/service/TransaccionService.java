package com.example.corebancario.service;

import com.example.corebancario.model.TipoTransaccion;

import java.math.BigDecimal;

public interface TransaccionService {
    void registrarTransaccion(BigDecimal monto, TipoTransaccion tipo);
}
