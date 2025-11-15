package com.example.corebancario.controller;

import com.example.corebancario.dto.SolicitudCompraDTO;
import com.example.corebancario.service.TarjetaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @PostMapping("/{id}/compra")
    public void realizarCompra(@PathVariable Long id, @RequestBody SolicitudCompraDTO solicitudCompraDTO) {
        tarjetaService.realizarCompra(id, solicitudCompraDTO);
    }
}
