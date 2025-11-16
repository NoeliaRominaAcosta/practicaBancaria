/*
 * Copyright (c) 2025, Noelia Romina Acosta
 * All rights reserved.
 */

package com.example.corebancario.service;

import com.example.corebancario.dto.PagoCuotaDTO;
import com.example.corebancario.model.Cuenta;
import com.example.corebancario.model.Prestamo;
import com.example.corebancario.repository.CuentaRepository;
import com.example.corebancario.repository.PrestamoRepository;
import com.example.corebancario.strategy.IInteresStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final CuentaRepository cuentaRepository;
    private final Map<String, IInteresStrategy> interesStrategies;
    private final TransaccionService transaccionService;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, CuentaRepository cuentaRepository, Map<String, IInteresStrategy> interesStrategies, TransaccionService transaccionService) {
        this.prestamoRepository = prestamoRepository;
        this.cuentaRepository = cuentaRepository;
        this.interesStrategies = interesStrategies;
        this.transaccionService = transaccionService;
    }

    @Override
    @Transactional
    public void pagarCuota(Long prestamoId, PagoCuotaDTO pagoCuotaDTO) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Prestamo not found"));

        BigDecimal montoCuota = pagoCuotaDTO.monto();

        IInteresStrategy interesStrategy = interesStrategies.get("interesSimple"); // Default
        if (prestamo.getMonto().compareTo(new BigDecimal("10000")) > 0) {
            interesStrategy = interesStrategies.get("interesCompuesto");
        }

        BigDecimal interes = interesStrategy.calcularInteres(montoCuota); //sabe a cual estrategia llamar en base al get que hizo y luego cada strategy contiene un @Component se usa como la clave en el Map que el servicio usa para seleccionar la estrategia
        BigDecimal totalAPagar = montoCuota.add(interes);

        Cuenta cuenta = prestamo.getCliente().getCuentas().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No account found for customer"));

        if (cuenta.getBalance() < totalAPagar.doubleValue()) {
            throw new RuntimeException("Insufficient funds");
        }

        cuenta.setBalance(cuenta.getBalance() - totalAPagar.doubleValue());
        prestamo.setMonto(prestamo.getMonto().subtract(montoCuota));

        transaccionService.registrarTransaccion(totalAPagar, com.example.corebancario.model.TipoTransaccion.PAGO_PRESTAMO);

        cuentaRepository.save(cuenta);
        prestamoRepository.save(prestamo);
    }
}
