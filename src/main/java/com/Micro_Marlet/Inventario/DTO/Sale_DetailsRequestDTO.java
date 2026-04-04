package com.Micro_Marlet.Inventario.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor  // ← Obligatorio
@AllArgsConstructor
public class Sale_DetailsRequestDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer quantity;
}