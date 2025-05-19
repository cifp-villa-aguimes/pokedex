package com.damw.pokedex.service;

import com.damw.pokedex.model.Pokemon;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

public interface PokemonService {
    /**
     * Devuelve una lista de todos los Pokémon.
     *
     * @return Lista de Pokémon.
     */
    List<Pokemon> getAllPokemons();

    /**
     * Devuelve un Pokémon por su ID.
     *
     * @param id ID del Pokémon.
     * @return Pokémon correspondiente al ID.
     */
    Optional<Pokemon> getPokemonById(Long id);

    /**
     * Guarda un Pokémon en la base de datos.
     *
     * @param p Pokémon a guardar.
     * @return Pokémon guardado.
     */
    Pokemon savePokemon(Pokemon p);

    /**
     * Actualiza un Pokémon existente.
     *
     * @param id ID del Pokémon a actualizar.
     * @param p  Pokémon con los nuevos datos.
     * @return Pokémon actualizado.
     */
    Pokemon updatePokemon(Long id, Pokemon p);

    /**
     * Elimina un Pokémon por su ID.
     *
     * @param id ID del Pokémon a eliminar.
     */
    void deletePokemon(Long id);

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