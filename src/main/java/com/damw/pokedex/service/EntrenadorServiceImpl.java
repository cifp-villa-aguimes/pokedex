package com.damw.pokedex.service;

import com.damw.pokedex.model.Entrenador;
import com.damw.pokedex.repository.EntrenadorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
/**
 * Implementación del servicio de Entrenador.
 * Proporciona métodos para gestionar entrenadores en la base de datos.
 */
public class EntrenadorServiceImpl extends AbstractCrudService<Entrenador, Long>
        implements EntrenadorService {
    /**
     * Constructor que inicializa el repositorio de entrenadores.
     *
     * @param repo el repositorio de entrenadores
     */
    public EntrenadorServiceImpl(EntrenadorRepository repo) {
        super(repo);
    }

    /**
     * Actualiza completamente un Entrenador.
     * Al gestionar la colección de pokemons, se asegura que Hibernate aplique
     * correctamente
     * la eliminación de huérfanos (orphanRemoval). Si la colección 'pokemons' no se
     * incluye
     * en la actualización, Hibernate eliminará los pokemons que ya no estén
     * asociados al entrenador.
     */
    @Override
    @Transactional
    public Entrenador update(Long id, Entrenador ent) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Entrenador con ID " + id + " no existe.");
        }
        ent.setId(id);
        return repo.save(ent);
    }
}