package com.example.corebancario.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("interesCompuesto")
public class InteresCompuestoStrategy implements IInteresStrategy {

    private static final BigDecimal TASA_INTERES = new BigDecimal("0.1");

    @Override
    public BigDecimal calcularInteres(BigDecimal monto) {
        // This is a simplified version of compound interest calculation
        return monto.multiply(TASA_INTERES);
    }
}
