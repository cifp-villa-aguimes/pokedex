package com.damw.pokedex.repository;

import com.damw.pokedex.model.Pokemon;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    /**
     * Busca Pokémon por tipo (contiene, case-insensitive) y nivel en rango, con
     * orden especificado.
     * 
     * @param tipo     texto parcial del tipo de Pokémon
     * @param nivelMin nivel mínimo (inclusive)
     * @param nivelMax nivel máximo (inclusive)
     * @param sort     criterio de ordenación (campo y dirección)
     */
    // 📌 MUY IMPORTANTE: El nombre del método NO es solo estético.
    // Spring Data JPA lo interpreta para generar la consulta automáticamente.
    List<Pokemon> findByTipoContainingIgnoreCaseAndNivelBetween(
            String tipo,
            int nivelMin,
            int nivelMax,
            Sort sort);
}
