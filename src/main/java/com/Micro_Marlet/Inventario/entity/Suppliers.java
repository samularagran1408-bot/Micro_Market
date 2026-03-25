package com.Micro_Marlet.Inventario.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "suppliers")
public class Suppliers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nit", unique = true, nullable = false)
    private Long nit;  // NIT como Long porque es PRIMARY KEY AUTO_INCREMENT
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "status")
    private Boolean status = true;  // Valor por defecto TRUE
    
    // Relación ManyToMany con Product 
    @ManyToMany(mappedBy = "suppliers")
    private Set<Products> products;
}