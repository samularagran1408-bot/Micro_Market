package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeesResponseDTO {

    private Long id;
    private String idNumber;
    private String fullName;
    private String position;
    private LocalDate hireDate;
    private BigDecimal salary;
    private Boolean status;
}
