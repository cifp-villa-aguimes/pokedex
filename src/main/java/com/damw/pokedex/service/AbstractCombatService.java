package com.damw.pokedex.service;

import com.damw.pokedex.model.Combate;
import com.damw.pokedex.model.CombateTurno;
import com.damw.pokedex.model.EstadoCombate;
import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.repository.CombateRepository;
import com.damw.pokedex.repository.CombateTurnoRepository;
import com.damw.pokedex.repository.PokemonRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Transactional
public abstract class AbstractCombatService implements CombatSessionService {
    // Dependencias JPA inyectadas por constructor
    protected final PokemonRepository pokemonRepo;
    protected final CombateRepository combateRepo;
    protected final CombateTurnoRepository turnoRepo;

    /**
     * Constructor para inyección de dependencias.
     */
    public AbstractCombatService(
            PokemonRepository pokemonRepo,
            CombateRepository combateRepo,
            CombateTurnoRepository turnoRepo) {
        this.pokemonRepo = pokemonRepo;
        this.combateRepo = combateRepo;
        this.turnoRepo = turnoRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Combate> getActiveCombats() {
        // Lógica genérica para obtener combates activos
        return combateRepo.findByEstado(EstadoCombate.EN_CURSO);
    }

    @Override
    public Long startCombat(Long pokemonAId, Long pokemonBId) {
        // Lógica genérica de inicio de combate
        Pokemon pokemonA = pokemonRepo.findById(pokemonAId).orElseThrow();
        Pokemon pokemonB = pokemonRepo.findById(pokemonBId).orElseThrow();
        Combate combate = new Combate(pokemonA, pokemonB);
        combateRepo.save(combate);
        return combate.getId();
    }

    @Override
    public Combate executeTurn(Long combateId) {
        Combate combate = combateRepo.findById(combateId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Combate no encontrado con id " + combateId));

        if (combate.getEstado() != EstadoCombate.EN_CURSO) {
            throw new IllegalStateException("No se puede ejecutar turno: combate ya finalizado");
        }

        // Incrementa el turno antes de decidir roles (turno 1 = atacante original)
        int nextTurn = combate.getTurnoActual() + 1;
        combate.setTurnoActual(nextTurn);

        // Determina atacante/defensor alternando por número de turno (impares pokemonA,
        // pares pokemonB)
        boolean pokemonAAtaca = (nextTurn % 2 != 0);
        Pokemon atacante = pokemonAAtaca
                ? combate.getPokemonA()
                : combate.getPokemonB();
        Pokemon defensor = pokemonAAtaca
                ? combate.getPokemonB()
                : combate.getPokemonA();

        // Salud antes del ataque según jugador
        int saludAntesAtk = pokemonAAtaca ? combate.getSaludPokemonA() : combate.getSaludPokemonB();
        int saludAntesDef = pokemonAAtaca ? combate.getSaludPokemonB() : combate.getSaludPokemonA();

        // Cálculo y aplicación de daño
        int damage = calculateDamage(atacante, defensor);
        int newDefHealth = Math.max(saludAntesDef - damage, 0);
        // Aplica el daño en la salud de combate, no en la entidad Pokémon
        if (pokemonAAtaca) {
            combate.setSaludPokemonB(newDefHealth);
        } else {
            combate.setSaludPokemonA(newDefHealth);
        }

        // Guarda el turno con atacante explícito
        turnoRepo.save(new CombateTurno(
                combate,
                combate.getTurnoActual(),
                damage,
                saludAntesAtk,
                saludAntesDef,
                atacante));

        // Marca finalizado si salud <= 0
        if (newDefHealth <= 0) {
            combate.setEstado(EstadoCombate.FINALIZADO);
        }

        // Persiste y devuelve el combate actualizado
        return combateRepo.save(combate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Combate> getCombatById(Long id) {
        return combateRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CombateTurno> getTurnsForCombat(Long combateId) {
        return turnoRepo.findByCombate_Id(combateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CombateTurno> getTurnsByAttacker(Long atacanteId) {
        return turnoRepo.findByAtacante_Id(atacanteId);
    }

    /**
     * Cálculo de daño concreto, implementado por la subclase.
     */
    protected abstract int calculateDamage(Pokemon attacker, Pokemon defender);
}
