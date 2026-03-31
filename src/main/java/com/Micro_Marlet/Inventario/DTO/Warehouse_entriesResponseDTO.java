package com.Micro_Marlet.Inventario.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Warehouse_entriesResponseDTO {
    private Long id;
    private Integer quantity;
    private LocalDate entryDate;
    private Long productId;
    private String productName;
    private String productBarcode;
    private Long supplierNit;
    private String supplierName;
    private Integer updatedStock;
}
