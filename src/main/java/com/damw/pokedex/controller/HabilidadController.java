package com.damw.pokedex.controller;

import com.damw.pokedex.model.Habilidad;
import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.service.HabilidadService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/habilidades")
@Validated
public class HabilidadController {
    private static final Logger log = LoggerFactory.getLogger(HabilidadController.class);

    private final HabilidadService habilidadSvc;

    public HabilidadController(HabilidadService habilidadSvc) {
        this.habilidadSvc = habilidadSvc;
    }

    /** GET /api/v1/habilidades → lista todas las habilidades */
    @GetMapping
    public ResponseEntity<List<Habilidad>> getAll() {
        log.info("Fetching all habilidades");
        log.debug("GET /api/v1/habilidades");
        return ResponseEntity.ok(habilidadSvc.getAll());
    }

    /** GET /api/v1/habilidades/{id} → obtiene una habilidad por ID o 404 */
    @GetMapping("/{id}")
    public ResponseEntity<Habilidad> getById(@PathVariable @Positive Long id) {
        log.info("Fetching habilidad with id: {}", id);
        log.debug("GET /api/v1/habilidades/{}", id);
        return habilidadSvc.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/habilidades/{id}/pokemons
     * Lista todos los Pokémon que poseen una determinada habilidad.
     */
    @GetMapping("/{id}/pokemons")
    public ResponseEntity<List<Pokemon>> getPokemonsByHabilidad(@PathVariable @Positive Long id) {
        log.info("Fetching pokemons for habilidad with id: {}", id);
        log.debug("GET /api/v1/habilidades/{}/pokemons", id);
        // Buscar la habilidad o lanzar 404 si no existe
        Habilidad h = habilidadSvc.getById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Habilidad no encontrada con id " + id));
        // Devolver la lista de Pokémon asociados
        List<Pokemon> pokemons = new ArrayList<>(h.getPokemons());
        return ResponseEntity.ok(pokemons);
    }

    /** POST /api/v1/habilidades → crea una nueva habilidad (201 Created) */
    @PostMapping
    public ResponseEntity<Habilidad> create(@Valid @RequestBody Habilidad h) {
        log.info("Creating new habilidad: {}", h);
        log.debug("POST /api/v1/habilidades");
        log.debug("Request body: {}", h);
        Habilidad created = habilidadSvc.save(h);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/v1/habilidades/{id} → actualiza una habilidad existente o 404 */
    @PutMapping("/{id}")
    public ResponseEntity<Habilidad> update(@PathVariable @Positive Long id, @Valid @RequestBody Habilidad h) {
        log.info("Updating habilidad with id: {}", id);
        log.debug("PUT /api/v1/habilidades/{}", id);
        log.debug("Request body: {}", h);
        return habilidadSvc.getById(id)
                .map(existing -> {
                    Habilidad updated = habilidadSvc.update(id, h);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/v1/habilidades/{id} → elimina una habilidad o 404 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        log.info("Deleting habilidad with id: {}", id);
        log.debug("DELETE /api/v1/habilidades/{}", id);
        return habilidadSvc.getById(id)
                .map(existing -> {
                    habilidadSvc.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}