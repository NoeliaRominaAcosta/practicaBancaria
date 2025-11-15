package com.example.corebancario.strategy;

import java.math.BigDecimal;

public interface IInteresStrategy {
    BigDecimal calcularInteres(BigDecimal monto);
}
