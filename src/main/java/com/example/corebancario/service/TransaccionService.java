/*
 * Copyright (c) 2025, Noelia Romina Acosta
 * All rights reserved.
 */

package com.example.corebancario.service;

import com.example.corebancario.model.TipoTransaccion;

import java.math.BigDecimal;

public interface TransaccionService {
    void registrarTransaccion(BigDecimal monto, TipoTransaccion tipo);
}
