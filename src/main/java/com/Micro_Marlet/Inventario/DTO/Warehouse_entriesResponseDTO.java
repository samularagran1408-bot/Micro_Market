package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;

@Data
public class Warehouse_entriesResponseDTO {
    private Long id;
    private Integer quantity;
    private String entryDate;
    private Long productId;
    private String productName; // Agregar el nombre del producto
    private Long supplierNit;
    private String supplierName; // Agregar el nombre del proveedor    
}
