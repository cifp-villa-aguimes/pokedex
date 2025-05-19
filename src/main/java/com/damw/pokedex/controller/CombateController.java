package com.damw.pokedex.controller;

import com.damw.pokedex.model.Combate;
import com.damw.pokedex.model.CombateTurno;
import com.damw.pokedex.service.CombatSessionService;

import jakarta.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/combates")
@Validated
public class CombateController {

    private final CombatSessionService combatSvc;

    public CombateController(CombatSessionService combatSvc) {
        this.combatSvc = combatSvc;
    }

    /**
     * GET /api/v2/combates/en-curso → Lista todos los combates que están en curso
     * (EstadoCombate.EN_CURSO)
     */
    @GetMapping("/en-curso")
    public ResponseEntity<List<Combate>> getActiveCombats() {
        List<Combate> activos = combatSvc.getActiveCombats();
        return ResponseEntity.ok(activos);
    }

    /**
     * POST /api/v2/combates?playerAId={playerAId}&playerBId={playerBId} →
     * Inicia un nuevo combate entre dos jugadores identificados como A y B,
     * devolviendo el ID del combate generado.
     */
    @PostMapping
    public ResponseEntity<Long> startCombat(
            @RequestParam("playerAId") @Positive(message = "playerAId debe ser positivo") Long playerAId,
            @RequestParam("playerBId") @Positive(message = "playerBId debe ser positivo") Long playerBId) {
        Long combateId = combatSvc.startCombat(playerAId, playerBId);
        return ResponseEntity.status(HttpStatus.CREATED).body(combateId);
    }

    /**
     * POST /api/v2/combates/{id}/turno → Ejecuta el siguiente turno en el combate y
     * devuelve el registro del turno.
     */
    @PostMapping("/{id}/turno")
    public ResponseEntity<CombateTurno> nextTurn(
            @PathVariable @Positive(message = "El id del combate debe ser positivo") Long id) {
        Combate updated = combatSvc.executeTurn(id);
        int turnoNum = updated.getTurnoActual();
        return combatSvc.getTurnsForCombat(id).stream()
                .filter(t -> t.getNumeroTurno() == turnoNum)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/v2/combates/{id} → Obtiene el estado del combate (cabecera).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Combate> getCombat(
            @PathVariable @Positive(message = "El id del combate debe ser positivo") Long id) {
        return combatSvc.getCombatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v2/combates/{id}/turnos → Lista todos los turnos registrados en el
     * combate.
     */
    @GetMapping("/{id}/turnos")
    public ResponseEntity<List<CombateTurno>> listTurns(
            @PathVariable @Positive(message = "El id del combate debe ser positivo") Long id) {
        List<CombateTurno> turnos = combatSvc.getTurnsForCombat(id);
        return ResponseEntity.ok(turnos);
    }

    /**
     * GET /api/v2/combates/turnos/por-atacante?atacanteId={atacanteId}
     * Recupera todos los turnos en los que el Pokémon con el ID dado actuó como
     * atacante.
     */
    @GetMapping("/turnos/por-atacante")
    public ResponseEntity<List<CombateTurno>> getTurnsByAttacker(
            @RequestParam("atacanteId") @Positive(message = "atacanteId debe ser positivo") Long atacanteId) {
        List<CombateTurno> turnos = combatSvc.getTurnsByAttacker(atacanteId);
        return ResponseEntity.ok(turnos);
    }
}
