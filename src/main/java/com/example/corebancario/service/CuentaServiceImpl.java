package com.example.corebancario.service;

import com.example.corebancario.dto.RespuestaBalanceDTO;
import com.example.corebancario.model.Cuenta;
import com.example.corebancario.repository.CuentaRepository;
import org.springframework.stereotype.Service;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public RespuestaBalanceDTO getBalance(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta not found"));
        return new RespuestaBalanceDTO(cuenta.getBalance());
    }
}
