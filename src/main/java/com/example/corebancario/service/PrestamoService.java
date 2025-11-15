package com.example.corebancario.service;

import com.example.corebancario.dto.PagoCuotaDTO;

public interface PrestamoService {
    void pagarCuota(Long prestamoId, PagoCuotaDTO pagoCuotaDTO);
}
