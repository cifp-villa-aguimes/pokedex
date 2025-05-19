package com.damw.pokedex.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * GlobalExceptionHandler
 *
 * Esta clase actúa como un "advice" global para todos los controladores REST de
 * la aplicación.
 * Al estar anotada con @RestControllerAdvice, Spring la detecta y registra
 * automáticamente
 * como un interceptor de excepciones que ocurran en cualquier clase marcada
 * con @RestController
 * (o @Controller).
 *
 * Principales componentes:
 * - @RestControllerAdvice: combina @ControllerAdvice y @ResponseBody,
 * permitiendo que los métodos de manejo de excepciones devuelvan directamente
 * objetos o respuestas HTTP sin necesidad de una vista.
 * - @ExceptionHandler(IllegalStateException.class): especifica que el método
 * handleIllegalState se ejecutará cuando un controlador lance una
 * IllegalStateException.
 *
 * Flujo de ejecución de la excepción:
 * 1. Un endpoint de un controlador lanza IllegalStateException durante el
 * manejo de la petición.
 * 2. Spring busca un método @ExceptionHandler de ese tipo en el propio
 * controlador.
 * 3. Si no lo encuentra, Spring recurre a las clases anotadas
 * con @ControllerAdvice/@RestControllerAdvice.
 * 4. Al encontrar handleIllegalState en esta clase, lo invoca pasando la
 * excepción como parámetro.
 * 5. El método construye un ResponseEntity<String> con estado HTTP 409
 * (Conflict)
 * y el mensaje de la excepción en el cuerpo.
 * 6. Esa ResponseEntity se envía al cliente como respuesta de la petición HTTP.
 *
 * Ventajas de este enfoque:
 * - Centraliza la gestión de errores para toda la API, evitando duplicaciones.
 * - Mantiene los controladores limpios de lógica de manejo de excepciones.
 * - Facilita el mantenimiento y la consistencia en las respuestas de error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura IllegalStateException lanzada desde cualquier controlador
     * y devuelve HTTP 409 Conflict con el mensaje de error.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
