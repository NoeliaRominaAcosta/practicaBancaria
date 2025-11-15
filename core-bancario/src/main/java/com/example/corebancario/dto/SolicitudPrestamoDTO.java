package com.example.corebancario.dto;

import java.math.BigDecimal;

public record SolicitudPrestamoDTO(Long clienteId, BigDecimal monto, String tipoPrestamo) {
}
