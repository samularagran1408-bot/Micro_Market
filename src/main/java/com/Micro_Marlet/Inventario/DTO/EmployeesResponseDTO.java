package com.Micro_Marlet.Inventario.DTO;

import com.Micro_Marlet.Inventario.entity.EmployeeEstado;
import com.Micro_Marlet.Inventario.entity.EmployeePosition;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeesResponseDTO {

    private Long id;

    private String idNumber;

    private String nombre;

    private String email;

    private String telefono;

    private LocalDateTime fechaRegistro;

    private LocalDateTime ultimoAcceso;

    private EmployeeEstado estado;

    private String fotoPerfil;

    private EmployeePosition position;

    private LocalDate hireDate;

    private BigDecimal salary;
}
