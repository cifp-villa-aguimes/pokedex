package com.damw.pokedex.service;

import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.repository.PokemonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepo;

    public PokemonService(PokemonRepository pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }

    @Transactional(readOnly = true)
    public List<Pokemon> getAllPokemons() {
        return pokemonRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pokemon> getPokemonById(Long id) {
        return pokemonRepo.findById(id);
    }

    public Pokemon savePokemon(Pokemon p) {
        return pokemonRepo.save(p);
    }

    public Pokemon updatePokemon(Long id, Pokemon p) {
        p.setId(id);
        return pokemonRepo.save(p);
    }

    public void deletePokemon(Long id) {
        pokemonRepo.deleteById(id);
    }

    /**
     * Busca Pokémon según parámetros opcionales de filtro y orden.
     */
    public List<Pokemon> searchPokemons(String tipo, Integer nivelMin, Integer nivelMax, Sort sort) {
        String tipoFilter = (tipo == null ? "" : tipo);
        int min = (nivelMin == null ? 0 : nivelMin);
        int max = (nivelMax == null ? Integer.MAX_VALUE : nivelMax);
        return pokemonRepo.findByTipoContainingIgnoreCaseAndNivelBetween(
                tipoFilter, min, max, sort);
    }
}