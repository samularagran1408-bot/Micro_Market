package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;

@Data
public class ProductsResponseDTO {
    private Long id;
    private String barcode;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean status;
    private Long categoryId;
    private String categoryName;  // ← Este campo debe existir
}
