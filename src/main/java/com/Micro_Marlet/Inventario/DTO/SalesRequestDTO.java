package com.Micro_Marlet.Inventario.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor  // ← Obligatorio para deserialización
@AllArgsConstructor // ← Opcional pero recomendado
public class SalesRequestDTO {
    
    @NotNull(message = "El ID del empleado es obligatorio")
    private Long employeeId;
    
    private LocalDateTime date;  // Opcional, si no viene usa fecha actual
    
    @NotNull(message = "Los detalles de la venta son obligatorios")
    private List<Sale_DetailsRequestDTO> details;
}