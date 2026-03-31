package com.Micro_Marlet.Inventario.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Warehouse_entriesRequestDTO {
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer quantity;

    @NotNull(message = "La fecha de entrada es obligatoria")
    private LocalDate entryDate;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotNull(message = "El NIT del proveedor es obligatorio")
    private Long supplierNit;
}
