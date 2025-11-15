package com.example.corebancario.service;

import com.example.corebancario.model.TipoTransaccion;
import com.example.corebancario.model.Transaccion;
import com.example.corebancario.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;

    public TransaccionServiceImpl(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public void registrarTransaccion(BigDecimal monto, TipoTransaccion tipo) {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(monto);
        transaccion.setTipo(tipo);
        transaccion.setFecha(LocalDateTime.now());
        transaccionRepository.save(transaccion);
    }
}
