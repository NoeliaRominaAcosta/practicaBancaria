package com.example.corebancario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @Email
    @NotEmpty
    private String email;

    private Integer scoreCrediticio;

    // Private constructor for builder
    private Cliente(Builder builder) {
        this.nombre = builder.nombre;
        this.email = builder.email;
        this.scoreCrediticio = builder.scoreCrediticio;
    }

    public Cliente() {

    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getScoreCrediticio() {
        return scoreCrediticio;
    }

    public void setScoreCrediticio(Integer scoreCrediticio) {
        this.scoreCrediticio = scoreCrediticio;
    }

    // Builder class
    public static class Builder {
        private String nombre;
        private String email;
        private Integer scoreCrediticio;

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder scoreCrediticio(Integer scoreCrediticio) {
            this.scoreCrediticio = scoreCrediticio;
            return this;
        }

        public Cliente build() {
            return new Cliente(this);
        }
    }
}
