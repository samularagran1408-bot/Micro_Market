package com.Micro_Marlet.Inventario.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class CategoriesResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CategoryProductDTO> products;
}
