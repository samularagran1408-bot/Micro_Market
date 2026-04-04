package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.Micro_Marlet.Inventario.entity.Employees;

@Data
public class SalesRequestDTO {
    private LocalDateTime date = LocalDateTime.now();
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private Employees employee;
}
