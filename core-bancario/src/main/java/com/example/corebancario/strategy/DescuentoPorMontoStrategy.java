package com.example.corebancario.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DescuentoPorMontoStrategy implements IDescuentoStrategy {

    private static final BigDecimal MONTO_MINIMO = new BigDecimal("5000");
    private static final BigDecimal PORCENTAJE_DESCUENTO = new BigDecimal("0.05");

    @Override
    public BigDecimal aplicarDescuento(BigDecimal monto) {
        return monto.multiply(PORCENTAJE_DESCUENTO);
    }

    @Override
    public boolean esAplicable(BigDecimal monto) {
        return monto.compareTo(MONTO_MINIMO) > 0;
    }
}
