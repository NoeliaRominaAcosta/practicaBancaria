/*
 * Copyright (c) 2025, Noelia Romina Acosta
 * All rights reserved.
 */

package com.example.corebancario.strategy;

import java.math.BigDecimal;

public interface IInteresStrategy {
    BigDecimal calcularInteres(BigDecimal monto);
}
