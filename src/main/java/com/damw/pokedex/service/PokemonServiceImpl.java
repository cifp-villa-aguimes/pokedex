package com.damw.pokedex.service;

import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.repository.PokemonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // Indica que la clase es un servicio y que las transacciones deben ser
               // manejadas
/**
 * Implementación del servicio de Pokémon.
 * Proporciona métodos para gestionar Pokémon en la base de datos.
 */
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepo;

    public PokemonServiceImpl(PokemonRepository pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pokemon> getAllPokemons() {
        return pokemonRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Pokemon> getPokemonById(Long id) {
        return pokemonRepo.findById(id);
    }

    @Override
    public Pokemon savePokemon(Pokemon p) {
        return pokemonRepo.save(p);
    }

    @Override
    public Pokemon updatePokemon(Long id, Pokemon p) {
        p.setId(id);
        return pokemonRepo.save(p);
    }

    @Override
    public void deletePokemon(Long id) {
        pokemonRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pokemon> searchPokemons(String tipo, Integer nivelMin, Integer nivelMax, Sort sort) {
        String tipoFilter = (tipo == null ? "" : tipo);
        int min = (nivelMin == null ? 0 : nivelMin);
        int max = (nivelMax == null ? Integer.MAX_VALUE : nivelMax);
        return pokemonRepo.findByTipoContainingIgnoreCaseAndNivelBetween(
                tipoFilter, min, max, sort);
    }
}