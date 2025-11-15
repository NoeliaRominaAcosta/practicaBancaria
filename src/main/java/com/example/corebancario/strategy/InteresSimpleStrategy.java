package com.example.corebancario.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("interesSimple")
public class InteresSimpleStrategy implements IInteresStrategy {

    private static final BigDecimal TASA_INTERES = new BigDecimal("0.05");

    @Override
    public BigDecimal calcularInteres(BigDecimal monto) {
        return monto.multiply(TASA_INTERES);
    }
}
