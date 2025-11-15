package com.example.corebancario.strategy;

import com.example.corebancario.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class AprobacionScoreCrediticioStrategy implements IAprobacionStrategy {

    private static final int SCORE_MINIMO = 500;

    @Override
    public boolean esAprobado(Cliente cliente) {
        return cliente.getScoreCrediticio() != null && cliente.getScoreCrediticio() >= SCORE_MINIMO;
    }
}
