package com.damw.pokedex.service;

import com.damw.pokedex.model.Habilidad;
import com.damw.pokedex.repository.HabilidadRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
/**
 * Implementación del servicio de Habilidad.
 * Proporciona métodos para gestionar habilidades en la base de datos.
 */
public class HabilidadServiceImpl extends AbstractCrudService<Habilidad, Long> implements HabilidadService {

    /**
     * Constructor que inicializa el repositorio de habilidades.
     *
     * @param repo el repositorio de habilidades
     */
    public HabilidadServiceImpl(HabilidadRepository repo) {
        super(repo);
    }

}