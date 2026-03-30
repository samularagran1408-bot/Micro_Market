package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;

@Data
public class CategoriesResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean status;
}
