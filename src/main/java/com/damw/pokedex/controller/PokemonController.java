package com.damw.pokedex.controller;

import com.damw.pokedex.model.Pokemon;
import com.damw.pokedex.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/pokemons")
@Validated
public class PokemonController {
    private static final Logger log = LoggerFactory.getLogger(PokemonController.class);

    private final PokemonService pokemonSvc;

    public PokemonController(PokemonService pokemonSvc) {
        this.pokemonSvc = pokemonSvc;
    }

    /** GET /api/v1/pokemons → lista todos los Pokémon */
    @GetMapping
    public ResponseEntity<List<Pokemon>> getAll() {
        log.info("Fetching all pokemons");
        log.debug("GET /api/v1/pokemons");
        return ResponseEntity.ok(pokemonSvc.getAllPokemons());
    }

    /** GET /api/v1/pokemons/{id} → obtiene un Pokémon por ID o 404 */
    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getById(@PathVariable @Positive Long id) {
        log.info("Fetching pokemon with id: {}", id);
        log.debug("GET /api/v1/pokemons/{}", id);
        return pokemonSvc.getPokemonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/v1/pokemons → crea un nuevo Pokémon (201 Created) */
    @PostMapping
    public ResponseEntity<Pokemon> create(@Valid @RequestBody Pokemon p) {
        log.info("Creating new pokemon: {}", p);
        log.debug("POST /api/v1/pokemons");
        log.debug("Request body: {}", p);
        Pokemon created = pokemonSvc.savePokemon(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // POST /api/v1/pokemons/with-location → crea un nuevo Pokémon (201 Created +
    // Location)
    /**
     * Crea un nuevo Pokémon y devuelve una respuesta HTTP 201 Created.
     * 
     * La cabecera Location de la respuesta contiene la URL del recurso recién
     * creado,
     * siguiendo el patrón RESTful. El cuerpo de la respuesta incluye el Pokémon
     * creado.
     * 
     * Este enfoque es útil porque permite al cliente conocer la ubicación del nuevo
     * recurso
     * y acceder a él directamente si es necesario, facilitando la navegación y el
     * manejo
     * de recursos en aplicaciones REST.
     * 
     * @param p El objeto Pokémon a crear, recibido en el cuerpo de la petición.
     * @return ResponseEntity con el Pokémon creado y la cabecera Location apuntando
     *         al nuevo recurso.
     */
    @PostMapping("/with-location")
    public ResponseEntity<Pokemon> createWithLocation(@RequestBody Pokemon p) {
        log.info("Creating new pokemon with location: {}", p);
        log.debug("POST /api/v1/pokemons/with-location");
        log.debug("Request body: {}", p);
        Pokemon created = pokemonSvc.savePokemon(p);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    /** PUT /api/v1/pokemons/{id} → actualiza un Pokémon existente o 404 */
    @PutMapping("/{id}")
    public ResponseEntity<Pokemon> update(@PathVariable @Positive Long id, @Valid @RequestBody Pokemon p) {
        log.info("Updating pokemon with id: {} to {}", id, p);
        log.debug("PUT /api/v1/pokemons/{}", id);
        log.debug("Request body: {}", p);
        return pokemonSvc.getPokemonById(id)
                .map(existing -> {
                    Pokemon updated = pokemonSvc.updatePokemon(id, p);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/v1/pokemons/{id} → borra un Pokémon o 404 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        log.info("Deleting pokemon with id: {}", id);
        log.debug("DELETE /api/v1/pokemons/{}", id);
        return pokemonSvc.getPokemonById(id)
                .map(existing -> {
                    pokemonSvc.deletePokemon(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}