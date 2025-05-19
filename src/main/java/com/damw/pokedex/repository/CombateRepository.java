package com.damw.pokedex.repository;

import com.damw.pokedex.model.Combate;
import com.damw.pokedex.model.EstadoCombate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CombateRepository extends JpaRepository<Combate, Long> {
    /**
     * Recupera todos los combates con el estado indicado.
     * 
     * @param estado Estado del combate (EN_CURSO, FINALIZADO)
     * @return lista de combates en el estado dado
     */
    List<Combate> findByEstado(EstadoCombate estado);
}