package com.Micro_Marlet.Inventario.exception;
//
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s no encontrado con ID: %d", resource, id));
    }
}