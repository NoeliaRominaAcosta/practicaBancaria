package com.example.corebancario.service;

import com.example.corebancario.dto.RespuestaBalanceDTO;

public interface CuentaService {
    RespuestaBalanceDTO getBalance(Long cuentaId);
}
