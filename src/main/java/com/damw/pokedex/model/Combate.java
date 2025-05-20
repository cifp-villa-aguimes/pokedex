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
    @JoinColumn(name = "pokemon_a_id", nullable = false)
    private Pokemon pokemonA;

    @Column(name = "salud_pokemon_a", nullable = false)
    private int saludPokemonA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pokemon_b_id", nullable = false)
    private Pokemon pokemonB;

    @Column(name = "salud_pokemon_b", nullable = false)
    private int saludPokemonB;

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

    public Combate(Pokemon pokemonA, Pokemon pokemonB) {
        this.pokemonA = pokemonA;
        this.pokemonB = pokemonB;
        // Inicializa la salud de combate con la salud real de cada Pokémon
        this.saludPokemonA = pokemonA.getSalud();
        this.saludPokemonB = pokemonB.getSalud();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Pokemon getPokemonA() {
        return pokemonA;
    }

    public void setPokemonA(Pokemon pokemonA) {
        this.pokemonA = pokemonA;
    }

    public int getSaludPokemonA() {
        return saludPokemonA;
    }

    public void setSaludPokemonA(int saludPokemonA) {
        this.saludPokemonA = saludPokemonA;
    }

    public Pokemon getPokemonB() {
        return pokemonB;
    }

    public void setPokemonB(Pokemon pokemonB) {
        this.pokemonB = pokemonB;
    }

    public int getSaludPokemonB() {
        return saludPokemonB;
    }

    public void setSaludPokemonB(int saludPokemonB) {
        this.saludPokemonB = saludPokemonB;
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
