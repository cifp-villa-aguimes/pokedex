package com.damw.pokedex.service;

import com.damw.pokedex.model.Habilidad;
import com.damw.pokedex.repository.HabilidadRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HabilidadService {
    private final HabilidadRepository habilidadRepo;

    public HabilidadService(HabilidadRepository habilidadRepo) {
        this.habilidadRepo = habilidadRepo;
    }

    @Transactional(readOnly = true)
    public List<Habilidad> getAllHabilidades() {
        return habilidadRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Habilidad> getHabilidadById(Long id) {
        return habilidadRepo.findById(id);
    }

    public Habilidad saveHabilidad(Habilidad h) {
        return habilidadRepo.save(h);
    }

    public Habilidad updateHabilidad(Long id, Habilidad h) {
        h.setId(id);
        return habilidadRepo.save(h);
    }

    public void deleteHabilidad(Long id) {
        habilidadRepo.deleteById(id);
    }
}