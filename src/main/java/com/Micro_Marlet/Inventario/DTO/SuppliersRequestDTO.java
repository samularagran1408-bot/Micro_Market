package com.Micro_Marlet.Inventario.DTO;

import lombok.Data;

@Data
public class SuppliersRequestDTO {
    private String name;
    private String phone;
    private String email;
    private String address;
    private Boolean status;
}
