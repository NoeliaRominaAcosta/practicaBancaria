package com.example.corebancario.facade;

import com.example.corebancario.dto.SolicitudPrestamoDTO;
import com.example.corebancario.model.Prestamo;

public interface BankingFacadeService {
    Prestamo solicitarPrestamo(SolicitudPrestamoDTO solicitud);
}
