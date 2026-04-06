package com.Micro_Marlet.Inventario.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeesRequestDTO {
    
    @NotBlank(message = "El número de cédula es obligatorio")
    @Size(min = 6, max = 20, message = "La cédula debe tener entre 6 y 20 caracteres")
    private String idNumber;
    
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String fullName;
    
    @NotBlank(message = "El cargo es obligatorio")
    @Pattern(regexp = "ADMINISTRATOR|CASHIER|ASSISTANT", 
             message = "Cargo inválido. Debe ser: ADMINISTRATOR, CASHIER o ASSISTANT")
    private String position;
    
    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate hireDate;
    
    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser mayor a 0")
    private BigDecimal salary;
    
    private Boolean status;
}