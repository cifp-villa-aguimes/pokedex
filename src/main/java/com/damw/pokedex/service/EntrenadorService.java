package com.damw.pokedex.service;

import com.damw.pokedex.model.Entrenador;
import java.util.List;
import java.util.Optional;

public interface EntrenadorService {
    /**
     * Obtiene todos los entrenadores.
     *
     * @return una lista de entrenadores
     */
    List<Entrenador> getAllEntrenadores();

    /**
     * Obtiene un entrenador por su ID.
     *
     * @param id el ID del entrenador
     * @return un objeto Optional que contiene el entrenador si se encuentra, o
     *         vacío si no
     */
    Optional<Entrenador> getEntrenadorById(Long id);

    /**
     * Actualiza un entrenador existente.
     *
     * @param id el ID del entrenador a actualizar
     * @param e  el objeto Entrenador con los nuevos datos
     * @return el entrenador actualizado
     */
    Entrenador updateEntrenador(Long id, Entrenador e);

    /**
     * Guarda un nuevo entrenador o actualiza uno existente.
     *
     * @param e el objeto Entrenador a guardar
     * @return el entrenador guardado
     */
    Entrenador saveEntrenador(Entrenador e);

    /**
     * Elimina un entrenador por su ID.
     *
     * @param id el ID del entrenador a eliminar
     */
    void deleteEntrenador(Long id);
}
