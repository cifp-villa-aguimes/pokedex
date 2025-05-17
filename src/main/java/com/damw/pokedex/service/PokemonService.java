package com.damw.pokedex.service;

import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.repository.PokemonRepository;
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
}