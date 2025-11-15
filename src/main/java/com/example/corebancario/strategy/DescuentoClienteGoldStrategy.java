package com.example.corebancario.strategy;

import com.example.corebancario.model.Cliente;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DescuentoClienteGoldStrategy implements IDescuentoStrategy {

    private static final BigDecimal PORCENTAJE_DESCUENTO = new BigDecimal("0.15");
    private static final int SCORE_MINIMO = 700;

    @Override
    public BigDecimal aplicarDescuento(BigDecimal monto) {
        return monto.multiply(PORCENTAJE_DESCUENTO);
    }

    @Override
    public boolean esAplicable(Cliente cliente, BigDecimal monto) {
        return cliente != null && cliente.getScoreCrediticio() != null && cliente.getScoreCrediticio() >= SCORE_MINIMO;
    }
}
