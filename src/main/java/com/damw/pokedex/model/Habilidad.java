package com.damw.pokedex.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Clase que representa una habilidad de un Pokémon.
 * Un Pokémon puede tener múltiples habilidades.
 */
@Entity
@Table(name = "habilidades")
public class Habilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Inversa del ManyToMany
    @ManyToMany(mappedBy = "habilidades")
    @JsonIgnore
    private Set<Pokemon> pokemons = new HashSet<>();

    public Habilidad() {
    }

    public Habilidad(String nombre) {
        this.nombre = nombre;
    }

    // getters/setters…
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

    public Set<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(Set<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Habilidad habilidad = (Habilidad) o;
        return Objects.equals(id, habilidad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
