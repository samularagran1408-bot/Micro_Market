package com.Micro_Marlet.Inventario.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Sale_DetailsRequestDTO {

    @NotNull(message = "Falta el producto")
    private Long productId;

    @NotNull(message = "Falta la cantidad")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;
}
