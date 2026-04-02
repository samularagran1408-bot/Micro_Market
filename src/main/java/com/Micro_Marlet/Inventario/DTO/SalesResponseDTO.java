package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SalesResponseDTO {

    private Long id;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private Long employeeId;
    private String employeeName;
    private List<Sale_DetailsResponseDTO> details = new ArrayList<>();
}
