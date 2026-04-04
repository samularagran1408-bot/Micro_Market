package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;

@Data
public class SuppliersResponseDTO {
    private Long nit;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Boolean status;
}
