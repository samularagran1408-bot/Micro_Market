package com.Micro_Marlet.Inventario.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalesRequestDTO {

    @NotNull(message = "Indica el empleado")
    private Long employeeId;

    private LocalDateTime date;

    @NotEmpty(message = "Agrega al menos un detalle")
    @Valid
    private List<Sale_DetailsRequestDTO> details;
}
