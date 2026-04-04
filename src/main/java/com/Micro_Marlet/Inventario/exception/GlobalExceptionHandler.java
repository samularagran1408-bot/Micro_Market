package com.Micro_Marlet.Inventario.exception;
//
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Clase para guardar todos los mensajes de error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
        // Maneja malas busquedas
            MethodArgumentNotValidException ex, WebRequest request) {
        
        List<ValidationError> validationErrors = new ArrayList<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            
            // Generar mensaje amigable según el campo
            String friendlyMessage = getFriendlyMessage(fieldName, errorMessage);
            
            // Crear ValidationError con los 3 parámetros
            validationErrors.add(new ValidationError(
                fieldName,
                friendlyMessage,
                errorMessage
            ));
        });
        
        // Crear mapa de detalles a partir de los errores de validación
        Map<String, String> details = new HashMap<>();
        validationErrors.forEach(err -> details.put(err.getField(), err.getMessage()));
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Error de validación",
            "Se encontraron errores de validación en la solicitud",
            request.getDescription(false).replace("uri=", ""),
            details
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Error de negocio",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Recurso no encontrado",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno",
            "Ocurrió un error inesperado: " + ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // Método para generar mensajes amigables
    private String getFriendlyMessage(String field, String originalMessage) {
        Map<String, String> friendlyMessages = new HashMap<>();
        friendlyMessages.put("name", "El nombre del proveedor es obligatorio");
        friendlyMessages.put("phone", " El teléfono solo puede contener números");
        friendlyMessages.put("email", " El formato del correo no es válido");
        friendlyMessages.put("nit", " El NIT es obligatorio");
        friendlyMessages.put("address", " La dirección es obligatoria");
        
        return friendlyMessages.getOrDefault(field, originalMessage);
    }
}