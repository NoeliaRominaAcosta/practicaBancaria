package com.example.corebancario.controller;

import com.example.corebancario.dto.RespuestaBalanceDTO;
import com.example.corebancario.service.CuentaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{id}/balance")
    public RespuestaBalanceDTO getBalance(@PathVariable Long id) {
        return cuentaService.getBalance(id);
    }
}
