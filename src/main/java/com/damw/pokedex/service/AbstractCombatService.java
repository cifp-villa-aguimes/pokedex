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
    public Long startCombat(Long attackerId, Long defenderId) {
        // Lógica genérica de inicio de combate
        Pokemon atk = pokemonRepo.findById(attackerId).orElseThrow();
        Pokemon def = pokemonRepo.findById(defenderId).orElseThrow();
        Combate combate = new Combate(atk, def);
        combateRepo.save(combate);
        return combate.getId();
    }

    @Override
    public Combate executeTurn(Long combateId) {
        // Carga el combate
        Combate combate = combateRepo.findById(combateId).orElseThrow();

        // 1) Si ya está finalizado, lanzamos excepción
        if (combate.getEstado() != EstadoCombate.EN_CURSO) {
            throw new IllegalStateException("No se puede ejecutar turno: combate ya finalizado");
        }

        // 2) Tomamos salud antes del ataque
        int beforeAtk = combate.getAtacante().getSalud();
        int beforeDef = combate.getDefensor().getSalud();

        // 3) Calculamos daño y aplicamos con mínimo 0
        int damage = calculateDamage(combate.getAtacante(), combate.getDefensor());
        int newDefHealth = Math.max(beforeDef - damage, 0);
        combate.getDefensor().setSalud(newDefHealth);

        // 4) Incrementamos el número de turno
        combate.setTurnoActual(combate.getTurnoActual() + 1);

        // 5) Guardamos el detalle del turno
        turnoRepo.save(new CombateTurno(
                combate,
                combate.getTurnoActual(),
                damage,
                beforeAtk,
                beforeDef));

        // 6) Si la salud del defensor llega a 0, marcamos combate finalizado
        if (newDefHealth <= 0) {
            combate.setEstado(EstadoCombate.FINALIZADO);
        }

        // 7) Persistimos y devolvemos el estado actualizado
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

    /**
     * Cálculo de daño concreto, implementado por la subclase.
     */
    protected abstract int calculateDamage(Pokemon attacker, Pokemon defender);
}
