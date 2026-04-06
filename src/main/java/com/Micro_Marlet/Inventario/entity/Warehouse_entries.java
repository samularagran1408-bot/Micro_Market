package com.Micro_Marlet.Inventario.entity;

import lombok.Data;

import java.time.LocalDate;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "warehouse_entries")
public class Warehouse_entries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;  

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_nit", nullable = false)
    private Suppliers supplier;
}
