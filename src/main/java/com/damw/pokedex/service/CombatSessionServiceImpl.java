package com.damw.pokedex.service;

import org.springframework.stereotype.Service;
import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.repository.PokemonRepository;
import com.damw.pokedex.repository.CombateRepository;
import com.damw.pokedex.repository.CombateTurnoRepository;

@Service
public class CombatSessionServiceImpl extends AbstractCombatService {

    /**
     * Constructor para inyección de dependencias a la superclase.
     */
    public CombatSessionServiceImpl(
            PokemonRepository pokemonRepo,
            CombateRepository combateRepo,
            CombateTurnoRepository turnoRepo) {
        super(pokemonRepo, combateRepo, turnoRepo);
    }

    /**
     * Cálculo de daño sencillo: (nivel_atk * 2) - nivel_def; mínimo 1.
     */
    @Override
    protected int calculateDamage(Pokemon attacker, Pokemon defender) {
        int base = attacker.getNivel() * 2 - defender.getNivel();
        return Math.max(base, 1);
    }
}
