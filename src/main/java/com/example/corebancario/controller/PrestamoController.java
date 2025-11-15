package com.example.corebancario.controller;

import com.example.corebancario.dto.PagoCuotaDTO;
import com.example.corebancario.dto.SolicitudPrestamoDTO;
import com.example.corebancario.facade.BankingFacadeService;
import com.example.corebancario.model.Prestamo;
import com.example.corebancario.service.PrestamoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final BankingFacadeService bankingFacadeService;
    private final PrestamoService prestamoService;

    public PrestamoController(BankingFacadeService bankingFacadeService, PrestamoService prestamoService) {
        this.bankingFacadeService = bankingFacadeService;
        this.prestamoService = prestamoService;
    }

    @PostMapping("/solicitar")
    public Prestamo solicitarPrestamo(@RequestBody SolicitudPrestamoDTO solicitud) {
        return bankingFacadeService.solicitarPrestamo(solicitud);
    }

    @PostMapping("/{id}/pagar_cuota")
    public void pagarCuota(@PathVariable Long id, @RequestBody PagoCuotaDTO pagoCuotaDTO) {
        prestamoService.pagarCuota(id, pagoCuotaDTO);
    }
}
