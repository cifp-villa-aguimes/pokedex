package com.damw.pokedex.service;

import com.damw.pokedex.model.Entrenador;
import com.damw.pokedex.repository.EntrenadorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
/**
 * Implementación del servicio de Entrenador.
 * Proporciona métodos para gestionar entrenadores en la base de datos.
 */
public class EntrenadorServiceImpl implements EntrenadorService {
    private final EntrenadorRepository entrenadorRepo;

    public EntrenadorServiceImpl(EntrenadorRepository entrenadorRepo) {
        this.entrenadorRepo = entrenadorRepo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Entrenador> getAllEntrenadores() {
        return entrenadorRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Entrenador> getEntrenadorById(Long id) {
        return entrenadorRepo.findById(id);
    }

    @Override
    public Entrenador saveEntrenador(Entrenador e) {
        return entrenadorRepo.save(e);
    }

    @Override
    public Entrenador updateEntrenador(Long id, Entrenador e) {
        e.setId(id);
        return entrenadorRepo.save(e);
    }

    @Override
    public void deleteEntrenador(Long id) {
        entrenadorRepo.deleteById(id);
    }

}