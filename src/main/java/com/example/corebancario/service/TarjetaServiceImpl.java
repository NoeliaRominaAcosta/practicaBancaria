package com.example.corebancario.service;

import com.example.corebancario.dto.SolicitudCompraDTO;
import com.example.corebancario.model.Cuenta;
import com.example.corebancario.model.Tarjeta;
import com.example.corebancario.model.TipoTarjeta;
import com.example.corebancario.repository.TarjetaRepository;
import com.example.corebancario.strategy.IDescuentoStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final List<IDescuentoStrategy> descuentoStrategies;
    private final TransaccionService transaccionService;

    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository, List<IDescuentoStrategy> descuentoStrategies, TransaccionService transaccionService) {
        this.tarjetaRepository = tarjetaRepository;
        this.descuentoStrategies = descuentoStrategies;
        this.transaccionService = transaccionService;
    }

    @Override
    @Transactional
    public void realizarCompra(Long tarjetaId, SolicitudCompraDTO solicitudCompraDTO) {
        Tarjeta tarjeta = tarjetaRepository.findById(tarjetaId)
                .orElseThrow(() -> new RuntimeException("Tarjeta not found"));

        BigDecimal monto = solicitudCompraDTO.monto();

        if (tarjeta.getTipo() == TipoTarjeta.CREDITO) {
            if (tarjeta.getLimite() < monto.doubleValue()) {
                throw new RuntimeException("Credit limit exceeded");
            }
            tarjeta.setLimite(tarjeta.getLimite() - monto.doubleValue());
        } else { // DEBITO
            Cuenta cuenta = tarjeta.getCuenta();
            if (cuenta.getBalance() < monto.doubleValue()) {
                throw new RuntimeException("Insufficient funds");
            }
            cuenta.setBalance(cuenta.getBalance() - monto.doubleValue());
        }

        for (IDescuentoStrategy strategy : descuentoStrategies) {
            if (strategy.esAplicable(tarjeta.getCuenta().getCliente(), monto)) {
                BigDecimal descuento = strategy.aplicarDescuento(monto);
                // Apply discount logic here, e.g., cashback to the account
                tarjeta.getCuenta().setBalance(tarjeta.getCuenta().getBalance() + descuento.doubleValue());
            }
        }

        transaccionService.registrarTransaccion(monto, com.example.corebancario.model.TipoTransaccion.PAGO_TARJETA);

        tarjetaRepository.save(tarjeta);
    }
}
