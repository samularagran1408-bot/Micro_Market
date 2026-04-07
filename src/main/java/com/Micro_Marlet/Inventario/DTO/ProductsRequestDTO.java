package com.Micro_Marlet.Inventario.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductsRequestDTO {

    @NotBlank(message = "El código de barras es obligatorio")
    @Size(max = 100, message = "El código de barras no puede exceder los 100 caracteres")
    private String barcode;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 200, message = "El nombre del producto no puede exceder los 200 caracteres")
    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private Boolean status;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;
}
