package com.Micro_Marlet.Inventario.DTO;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Warehouse_entriesRequestDTO {
    @NotBlank(message = "La cantidad es obligatoria")
    @Size(max = 100)
    private Integer quantity;

    @NotBlank(message = "La fecha de entrada es obligatoria")
    private LocalDate entryDate;

    @NotBlank(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotBlank(message = "El NIT del proveedor es obligatorio")
    private Long supplierNit;
}
