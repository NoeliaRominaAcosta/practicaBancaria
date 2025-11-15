package com.example.corebancario.facade;

import com.example.corebancario.dto.SolicitudPrestamoDTO;
import com.example.corebancario.factory.LoanFactory;
import com.example.corebancario.model.Cliente;
import com.example.corebancario.model.Prestamo;
import com.example.corebancario.repository.ClienteRepository;
import com.example.corebancario.repository.PrestamoRepository;
import com.example.corebancario.strategy.IAprobacionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankingFacadeServiceImpl implements BankingFacadeService {

    private final ClienteRepository clienteRepository;
    private final PrestamoRepository prestamoRepository;
    private final LoanFactory loanFactory;
    private final IAprobacionStrategy aprobacionStrategy;

    public BankingFacadeServiceImpl(ClienteRepository clienteRepository, PrestamoRepository prestamoRepository, LoanFactory loanFactory, IAprobacionStrategy aprobacionStrategy) {
        this.clienteRepository = clienteRepository;
        this.prestamoRepository = prestamoRepository;
        this.loanFactory = loanFactory;
        this.aprobacionStrategy = aprobacionStrategy;
    }

    @Override
    @Transactional
    public Prestamo solicitarPrestamo(SolicitudPrestamoDTO solicitud) {
        Cliente cliente = clienteRepository.findById(solicitud.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente not found"));

        Prestamo prestamo = loanFactory.createLoan(solicitud, cliente);

        if (aprobacionStrategy.esAprobado(cliente)) {
            prestamo.setEstado(com.example.corebancario.model.EstadoPrestamo.APROBADO);
        } else {
            prestamo.setEstado(com.example.corebancario.model.EstadoPrestamo.RECHAZADO);
        }

        return prestamoRepository.save(prestamo);
    }
}
