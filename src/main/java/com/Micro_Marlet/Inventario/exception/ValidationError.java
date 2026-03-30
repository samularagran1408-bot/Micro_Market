package com.Micro_Marlet.Inventario.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor  // ← Esto genera un constructor con todos los campos
public class ValidationError {
    private String field;
    private String message;
    private String technicalMessage;
}