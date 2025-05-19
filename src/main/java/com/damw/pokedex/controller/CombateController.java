package com.damw.pokedex.controller;

import com.damw.pokedex.model.Combate;
import com.damw.pokedex.model.CombateTurno;
import com.damw.pokedex.service.CombatSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/combate")
public class CombateController {

    private final CombatSessionService combatSvc;

    public CombateController(CombatSessionService combatSvc) {
        this.combatSvc = combatSvc;
    }

    /**
     * GET /api/v2/combate/en-curso → Lista todos los combates que están en curso
     * (EstadoCombate.EN_CURSO)
     */
    @GetMapping("/en-curso")
    public ResponseEntity<List<Combate>> getActiveCombats() {
        List<Combate> activos = combatSvc.getActiveCombats();
        return ResponseEntity.ok(activos);
    }

    /**
     * POST /api/v2/combate?attackerId={attackerId}&defenderId={defenderId} → Inicia
     * un nuevo combate y devuelve el ID generado.
     */
    @PostMapping
    public ResponseEntity<Long> startCombat(
            @RequestParam Long attackerId,
            @RequestParam Long defenderId) {
        Long combateId = combatSvc.startCombat(attackerId, defenderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(combateId);
    }

    /**
     * POST /api/v2/combate/{id}/turno → Ejecuta el siguiente turno en el combate y
     * devuelve el registro del turno.
     */
    @PostMapping("/{id}/turno")
    public ResponseEntity<CombateTurno> nextTurn(@PathVariable Long id) {
        Combate updated = combatSvc.executeTurn(id);
        int turnoNum = updated.getTurnoActual();
        return combatSvc.getTurnsForCombat(id).stream()
                .filter(t -> t.getNumeroTurno() == turnoNum)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/v2/combate/{id} → Obtiene el estado del combate (cabecera).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Combate> getCombat(@PathVariable Long id) {
        return combatSvc.getCombatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v2/combate/{id}/turnos → Lista todos los turnos registrados en el
     * combate.
     */
    @GetMapping("/{id}/turnos")
    public ResponseEntity<List<CombateTurno>> listTurns(@PathVariable Long id) {
        List<CombateTurno> turnos = combatSvc.getTurnsForCombat(id);
        return ResponseEntity.ok(turnos);
    }
}
