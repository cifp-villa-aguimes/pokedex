package com.damw.pokedex.controller;

import com.damw.pokedex.model.Entrenador;
import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.service.EntrenadorService;

import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entrenadores")
@Validated
public class EntrenadorController {
    private static final Logger log = LoggerFactory.getLogger(EntrenadorController.class);

    private final EntrenadorService entrenadorSvc;

    public EntrenadorController(EntrenadorService entrenadorSvc) {
        this.entrenadorSvc = entrenadorSvc;
    }

    /** GET /api/v1/entrenadores → lista todos los entrenadores */
    @GetMapping
    public ResponseEntity<List<Entrenador>> getAll() {
        log.info("Fetching all entrenadores");
        log.debug("GET /api/v1/entrenadores");
        return ResponseEntity.ok(entrenadorSvc.getAllEntrenadores());
    }

    /** GET /api/v1/entrenadores/{id} → obtiene un entrenador por ID o 404 */
    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> getById(@PathVariable @Positive Long id) {
        log.info("Fetching entrenador with id: {}", id);
        log.debug("GET /api/v1/entrenadores/{}", id);
        return entrenadorSvc.getEntrenadorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/entrenadores/{id}/pokemons
     * Lista todos los Pokémon de un entrenador dado.
     */
    @GetMapping("/{id}/pokemons")
    public ResponseEntity<List<Pokemon>> getPokemons(@PathVariable @Positive Long id) {
        log.info("Fetching pokemons for entrenador with id: {}", id);
        log.debug("GET /api/v1/entrenadores/{}/pokemons", id);
        Entrenador e = entrenadorSvc.getEntrenadorById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Entrenador no encontrado con id " + id));
        // Devolver la lista de Pokémon asociados
        List<Pokemon> pokemons = e.getPokemons();
        return ResponseEntity.ok(pokemons);
    }

    /** POST /api/v1/entrenadores → crea un nuevo entrenador (201 Created) */
    @PostMapping
    public ResponseEntity<Entrenador> create(@Valid @RequestBody Entrenador e) {
        log.info("Creating new entrenador: {}", e);
        log.debug("POST /api/v1/entrenadores");
        log.debug("Request body: {}", e);
        Entrenador created = entrenadorSvc.saveEntrenador(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/v1/entrenadores/{id} → actualiza un entrenador existente o 404 */
    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> update(@PathVariable @Positive Long id,
            @Valid @RequestBody Entrenador e) {
        log.info("Updating entrenador with id: {}", id);
        log.debug("PUT /api/v1/entrenadores/{}", id);
        log.debug("Request body: {}", e);
        return entrenadorSvc.getEntrenadorById(id)
                .map(existing -> {
                    Entrenador updated = entrenadorSvc.updateEntrenador(id, e);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/v1/entrenadores/{id} → elimina un entrenador o 404 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        log.info("Deleting entrenador with id: {}", id);
        log.debug("DELETE /api/v1/entrenadores/{}", id);
        return entrenadorSvc.getEntrenadorById(id)
                .map(existing -> {
                    entrenadorSvc.deleteEntrenador(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
