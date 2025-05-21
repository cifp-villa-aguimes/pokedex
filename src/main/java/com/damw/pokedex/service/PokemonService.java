package com.damw.pokedex.service;

import com.damw.pokedex.model.Pokemon;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface PokemonService extends CrudService<Pokemon, Long> {
    /**
     * Busca Pokémon según parámetros opcionales de filtro y orden.
     *
     * @param tipo     Tipo de Pokémon (opcional).
     * @param nivelMin Nivel mínimo (opcional).
     * @param nivelMax Nivel máximo (opcional).
     * @param sort     Ordenación (opcional).
     * @return Lista de Pokémon que cumplen los criterios.
     */
    List<Pokemon> searchPokemons(String tipo, Integer nivelMin, Integer nivelMax, Sort sort);
}