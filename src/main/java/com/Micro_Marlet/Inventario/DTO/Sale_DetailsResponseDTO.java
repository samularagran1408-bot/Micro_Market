package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Sale_DetailsResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}