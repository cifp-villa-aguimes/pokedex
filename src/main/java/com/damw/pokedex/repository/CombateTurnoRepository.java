package com.damw.pokedex.repository;

import com.damw.pokedex.model.CombateTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CombateTurnoRepository extends JpaRepository<CombateTurno, Long> {

    /**
     * Recupera todos los turnos asociados a un combate.
     * 
     * @param combateId ID del combate
     * @return lista de CombateTurno
     */
    List<CombateTurno> findByCombate_Id(Long combateId);

    /**
     * Recupera todos los turnos en los que un Pokémon actuó como atacante.
     *
     * @param atacanteId ID del Pokémon atacante.
     * @return lista de CombateTurno donde aparece como atacante.
     */
    List<CombateTurno> findByAtacante_Id(Long atacanteId);
}
