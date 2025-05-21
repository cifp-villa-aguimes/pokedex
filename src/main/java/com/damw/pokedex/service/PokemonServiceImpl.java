package com.damw.pokedex.service;

import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.repository.PokemonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // Indica que la clase es un servicio y que las transacciones deben ser
               // manejadas
/**
 * Implementación del servicio de Pokémon.
 * Proporciona métodos para gestionar Pokémon en la base de datos.
 */
public class PokemonServiceImpl extends AbstractCrudService<Pokemon, Long> implements PokemonService {
    private final PokemonRepository pokemonRepo;

    /**
     * Constructor que inicializa el repositorio de Pokémon.
     *
     * @param repo el repositorio de Pokémon
     */
    public PokemonServiceImpl(PokemonRepository repo) {
        super(repo);
        this.pokemonRepo = repo;
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