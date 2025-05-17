package com.damw.pokedex.service;

import com.damw.pokedex.model.Entrenador;
import com.damw.pokedex.repository.EntrenadorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {
    private final EntrenadorRepository entrenadorRepo;

    public EntrenadorService(EntrenadorRepository entrenadorRepo) {
        this.entrenadorRepo = entrenadorRepo;
    }

    @Transactional(readOnly = true)
    public List<Entrenador> getAllEntrenadores() {
        return entrenadorRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Entrenador> getEntrenadorById(Long id) {
        return entrenadorRepo.findById(id);
    }

    public Entrenador saveEntrenador(Entrenador e) {
        return entrenadorRepo.save(e);
    }

    public Entrenador updateEntrenador(Long id, Entrenador e) {
        e.setId(id);
        return entrenadorRepo.save(e);
    }

    public void deleteEntrenador(Long id) {
        entrenadorRepo.deleteById(id);
    }

}