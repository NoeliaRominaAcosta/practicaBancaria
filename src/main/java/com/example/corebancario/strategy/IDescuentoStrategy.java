/*
 * Copyright (c) 2025, Noelia Romina Acosta
 * All rights reserved.
 */

package com.example.corebancario.strategy;

import com.example.corebancario.model.Cliente;

import java.math.BigDecimal;

public interface IDescuentoStrategy {
    BigDecimal aplicarDescuento(BigDecimal monto);
    boolean esAplicable(Cliente cliente, BigDecimal monto);
}
