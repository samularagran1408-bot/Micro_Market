package com.Micro_Marlet.Inventario.DTO;

import com.Micro_Marlet.Inventario.entity.EmployeePosition;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeesRequestDTO {

    private String idNumber;

    private String nombre;

    private String email;

    /** Solo obligatorio en alta; en actualización, si viene vacío o null, no se cambia el hash guardado. */
    private String passwordHash;

    private String telefono;

    private String fotoPerfil;

    private EmployeePosition position;

    private LocalDate hireDate;

    private BigDecimal salary;
}
