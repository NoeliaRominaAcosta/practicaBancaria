package com.example.corebancario.strategy;

import com.example.corebancario.model.Cliente;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DescuentoSupermercadoStrategy implements IDescuentoStrategy {

    private static final BigDecimal PORCENTAJE_DESCUENTO = new BigDecimal("0.1");

    @Override
    public BigDecimal aplicarDescuento(BigDecimal monto) {
        return monto.multiply(PORCENTAJE_DESCUENTO);
    }

    @Override
    public boolean esAplicable(Cliente cliente, BigDecimal monto) {
        // This is a simplified version, in a real scenario we would check the merchant type
        return true;
    }
}
