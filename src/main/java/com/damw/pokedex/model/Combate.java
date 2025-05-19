package com.damw.pokedex.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "combates")
public class Combate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "atacante_id", nullable = false)
    private Pokemon atacante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "defensor_id", nullable = false)
    private Pokemon defensor;

    @Column(name = "turno_actual", nullable = false)
    private int turnoActual = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCombate estado = EstadoCombate.EN_CURSO;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio = LocalDateTime.now();

    // Constructors
    public Combate() {
    }

    public Combate(Pokemon atacante, Pokemon defensor) {
        this.atacante = atacante;
        this.defensor = defensor;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Pokemon getAtacante() {
        return atacante;
    }

    public void setAtacante(Pokemon atacante) {
        this.atacante = atacante;
    }

    public Pokemon getDefensor() {
        return defensor;
    }

    public void setDefensor(Pokemon defensor) {
        this.defensor = defensor;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

    public EstadoCombate getEstado() {
        return estado;
    }

    public void setEstado(EstadoCombate estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
