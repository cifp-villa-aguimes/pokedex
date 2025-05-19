package com.damw.pokedex.service;

import com.damw.pokedex.model.Habilidad;
import java.util.List;
import java.util.Optional;

public interface HabilidadService {
    /**
     * Obtiene todas las habilidades.
     *
     * @return una lista de habilidades
     */
    List<Habilidad> getAllHabilidades();

    /**
     * Obtiene una habilidad por su ID.
     *
     * @param id el ID de la habilidad
     * @return un objeto Optional que contiene la habilidad si se encuentra, o
     *         vacío si no
     */
    Optional<Habilidad> getHabilidadById(Long id);

    /**
     * Guarda una nueva habilidad o actualiza una existente.
     *
     * @param h el objeto Habilidad a guardar
     * @return la habilidad guardada
     */
    Habilidad saveHabilidad(Habilidad h);

    /**
     * Actualiza una habilidad existente.
     *
     * @param id el ID de la habilidad a actualizar
     * @param h  el objeto Habilidad con los nuevos datos
     * @return la habilidad actualizada
     */
    Habilidad updateHabilidad(Long id, Habilidad h);

    /**
     * Elimina una habilidad por su ID.
     *
     * @param id el ID de la habilidad a eliminar
     */
    void deleteHabilidad(Long id);
}
