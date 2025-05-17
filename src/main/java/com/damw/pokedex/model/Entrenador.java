package com.damw.pokedex.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Clase que representa a un Entrenador Pokémon.
 * Un Entrenador puede tener múltiples Pokémons.
 */
@Entity
@Table(name = "entrenadores")
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // 1 Entrenador → N Pokémons
    @OneToMany(mappedBy = "entrenador", // atributo en la otra clase
            cascade = CascadeType.ALL, // persiste/borra en cascada
            orphanRemoval = true // elimina huérfanos
    )
    @JsonIgnore
    private List<Pokemon> pokemons = new ArrayList<>();

    // Constructores, getters y setters
    public Entrenador() {
    }

    public Entrenador(String nombre) {
        this.nombre = nombre;
    }

    // add/remove helper
    public void addPokemon(Pokemon p) {
        pokemons.add(p);
        p.setEntrenador(this);
    }

    public void removePokemon(Pokemon p) {
        pokemons.remove(p);
        p.setEntrenador(null);
    }

    // getters/setters
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

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Entrenador that = (Entrenador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
