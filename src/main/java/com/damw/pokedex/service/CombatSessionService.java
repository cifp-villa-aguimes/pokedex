package com.damw.pokedex.service;

import com.damw.pokedex.model.Combate;
import com.damw.pokedex.model.CombateTurno;
import java.util.List;
import java.util.Optional;

public interface CombatSessionService {
    /**
     * Devuelve todos los combates que están actualmente en curso.
     *
     * @return lista de combates con estado EN_CURSO
     */
    List<Combate> getActiveCombats();

    /**
     * Inicia un combate entre dos jugadores A y B.
     * 
     * @param pokemonAId ID del Pokémon jugador A.
     * @param pokemonBId ID del Pokémon jugador B.
     * @return ID del combate iniciado.
     */
    Long startCombat(Long pokemonAId, Long pokemonBId);

    /**
     * Ejecuta un turno en el combate indicado, registra el turno y actualiza el
     * estado.
     * Devuelve el Combate actualizado.
     * 
     * @param combateId ID del combate en curso.
     * @return El combate actualizado tras el turno.
     */
    Combate executeTurn(Long combateId);

    /**
     * Obtiene un combate por su ID.
     * 
     * @param id ID del combate.
     * @return Un objeto Optional que contiene el combate si existe, o vacío si no.
     */
    Optional<Combate> getCombatById(Long id);

    /**
     * Obtiene la lista de turnos de un combate.
     * 
     * @param combateId ID del combate.
     * @return Lista de turnos del combate.
     */
    List<CombateTurno> getTurnsForCombat(Long combateId);

    /**
     * Recupera todos los turnos en los que un Pokémon actuó como atacante.
     *
     * @param atacanteId ID del Pokémon atacante.
     * @return lista de CombateTurno donde aparece como atacante.
     */
    List<CombateTurno> getTurnsByAttacker(Long atacanteId);
}
