package com.damw.pokedex.service;

import com.damw.pokedex.model.Habilidad;
import com.damw.pokedex.repository.HabilidadRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
/**
 * Implementación del servicio de Habilidad.
 * Proporciona métodos para gestionar habilidades en la base de datos.
 */
public class HabilidadServiceImpl implements HabilidadService {
    private final HabilidadRepository habilidadRepo;

    public HabilidadServiceImpl(HabilidadRepository habilidadRepo) {
        this.habilidadRepo = habilidadRepo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Habilidad> getAllHabilidades() {
        return habilidadRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Habilidad> getHabilidadById(Long id) {
        return habilidadRepo.findById(id);
    }

    @Override
    public Habilidad saveHabilidad(Habilidad h) {
        return habilidadRepo.save(h);
    }

    @Override
    public Habilidad updateHabilidad(Long id, Habilidad h) {
        h.setId(id);
        return habilidadRepo.save(h);
    }

    @Override
    public void deleteHabilidad(Long id) {
        habilidadRepo.deleteById(id);
    }
}