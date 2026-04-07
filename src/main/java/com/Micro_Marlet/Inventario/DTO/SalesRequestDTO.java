package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalesRequestDTO {
    
    @NotNull(message = "El ID del empleado es obligatorio")
    private Long employeeId;
    
    private LocalDateTime date;
    
    @NotNull(message = "Los detalles de la venta son obligatorios")
    private List<Sale_DetailsRequestDTO> details;
}