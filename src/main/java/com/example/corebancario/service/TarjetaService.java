package com.example.corebancario.service;

import com.example.corebancario.dto.SolicitudCompraDTO;

public interface TarjetaService {
    void realizarCompra(Long tarjetaId, SolicitudCompraDTO solicitudCompraDTO);
}
