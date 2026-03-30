package com.Micro_Marlet.Inventario.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "products")
public class Products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;  

    @Column(name = "barcode", unique = true, nullable = false, length = 100)
    private String barcode;

    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "price", nullable = false)
    private Double price;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "status")
    private Boolean status = true;  
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;
    
    // Relación ManyToMany con Supplier 
    @ManyToMany
    @JoinTable(
        name = "products_suppliers",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_nit")
    )
    private Set<Suppliers> suppliers = new HashSet<>();
}
