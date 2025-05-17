package com.damw.pokedex.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Clase que representa a un Pokémon.
 * Un Pokémon puede tener múltiples habilidades y pertenece a un Entrenador.
 */
@Entity
@Table(name = "pokemons")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo;
    private int nivel;
    private int salud;

    // Many Pokémons → 1 Entrenador
    @ManyToOne
    @JoinColumn(name = "entrenador_id") // columna FK en la tabla pokemons
    private Entrenador entrenador;

    @ManyToMany
    @JoinTable(name = "pokemon_habilidad", // tabla intermedia
            joinColumns = @JoinColumn(name = "pokemon_id"), // FK a pokemon
            inverseJoinColumns = @JoinColumn(name = "habilidad_id") // FK a habilidad
    )
    private Set<Habilidad> habilidades = new HashSet<>();

    public Pokemon() {
    }

    public Pokemon(String nombre, String tipo, int nivel, int salud) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.salud = salud;
    }

    // getters y setters
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public Set<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Set<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
