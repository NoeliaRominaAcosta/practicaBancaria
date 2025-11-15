package com.example.corebancario.service;

import com.example.corebancario.dto.PagoCuotaDTO;
import com.example.corebancario.model.Cuenta;
import com.example.corebancario.model.Prestamo;
import com.example.corebancario.repository.CuentaRepository;
import com.example.corebancario.repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final CuentaRepository cuentaRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, CuentaRepository cuentaRepository) {
        this.prestamoRepository = prestamoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    @Transactional
    public void pagarCuota(Long prestamoId, PagoCuotaDTO pagoCuotaDTO) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Prestamo not found"));

        BigDecimal montoCuota = pagoCuotaDTO.monto();
        BigDecimal interes = montoCuota.multiply(prestamo.getTasaInteres());
        BigDecimal totalAPagar = montoCuota.add(interes);

        Cuenta cuenta = prestamo.getCliente().getCuentas().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No account found for customer"));

        if (cuenta.getBalance() < totalAPagar.doubleValue()) {
            throw new RuntimeException("Insufficient funds");
        }

        cuenta.setBalance(cuenta.getBalance() - totalAPagar.doubleValue());
        prestamo.setMonto(prestamo.getMonto().subtract(montoCuota));

        cuentaRepository.save(cuenta);
        prestamoRepository.save(prestamo);
    }
}
