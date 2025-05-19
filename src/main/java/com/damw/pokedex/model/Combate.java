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
    @JoinColumn(name = "player_a_id", nullable = false)
    private Pokemon playerA;

    @Column(name = "salud_player_a", nullable = false)
    private int saludPlayerA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_b_id", nullable = false)
    private Pokemon playerB;

    @Column(name = "salud_player_b", nullable = false)
    private int saludPlayerB;

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

    public Combate(Pokemon playerA, Pokemon playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        // Inicializa la salud de combate con la salud real de cada Pokémon
        this.saludPlayerA = playerA.getSalud();
        this.saludPlayerB = playerB.getSalud();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Pokemon getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Pokemon playerA) {
        this.playerA = playerA;
    }

    public int getSaludPlayerA() {
        return saludPlayerA;
    }

    public void setSaludPlayerA(int saludPlayerA) {
        this.saludPlayerA = saludPlayerA;
    }

    public Pokemon getPlayerB() {
        return playerB;
    }

    public void setPlayerB(Pokemon playerB) {
        this.playerB = playerB;
    }

    public int getSaludPlayerB() {
        return saludPlayerB;
    }

    public void setSaludPlayerB(int saludPlayerB) {
        this.saludPlayerB = saludPlayerB;
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
