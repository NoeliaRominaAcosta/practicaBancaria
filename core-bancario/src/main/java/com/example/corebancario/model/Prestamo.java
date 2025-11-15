package com.example.corebancario.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal monto;

    private BigDecimal tasaInteres;

    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private Prestamo(Builder builder) {
        this.monto = builder.monto;
        this.tasaInteres = builder.tasaInteres;
        this.estado = builder.estado;
        this.cliente = builder.cliente;
    }

    public Prestamo() {

    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Builder class
    public static class Builder {
        private BigDecimal monto;
        private BigDecimal tasaInteres;
        private EstadoPrestamo estado;
        private Cliente cliente;

        public Builder monto(BigDecimal monto) {
            this.monto = monto;
            return this;
        }

        public Builder tasaInteres(BigDecimal tasaInteres) {
            this.tasaInteres = tasaInteres;
            return this;
        }

        public Builder estado(EstadoPrestamo estado) {
            this.estado = estado;
            return this;
        }

        public Builder cliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Prestamo build() {
            return new Prestamo(this);
        }
    }
}
