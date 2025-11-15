package com.example.corebancario.factory;

import com.example.corebancario.dto.SolicitudPrestamoDTO;
import com.example.corebancario.model.Cliente;
import com.example.corebancario.model.EstadoPrestamo;
import com.example.corebancario.model.Prestamo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoanFactory {

    public Prestamo createLoan(SolicitudPrestamoDTO dto, Cliente cliente) {
        return switch (dto.tipoPrestamo()) {
            case "PERSONAL" -> new Prestamo.Builder()
                    .cliente(cliente)
                    .monto(dto.monto())
                    .tasaInteres(new BigDecimal("0.1"))
                    .estado(EstadoPrestamo.PENDIENTE)
                    .build();
            case "ADELANTO" -> new Prestamo.Builder()
                    .cliente(cliente)
                    .monto(dto.monto())
                    .tasaInteres(new BigDecimal("0.2"))
                    .estado(EstadoPrestamo.PENDIENTE)
                    .build();
            default -> throw new IllegalArgumentException("Invalid loan type: " + dto.tipoPrestamo());
        };
    }
}
