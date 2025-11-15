package com.example.corebancario.strategy;

import java.math.BigDecimal;

public interface IDescuentoStrategy {
    BigDecimal aplicarDescuento(BigDecimal monto);
    boolean esAplicable(BigDecimal monto);
}
