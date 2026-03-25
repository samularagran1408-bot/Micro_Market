package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SalesRequestDTO {
    private LocalDateTime date = LocalDateTime.now();
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private Employees employee;
}
