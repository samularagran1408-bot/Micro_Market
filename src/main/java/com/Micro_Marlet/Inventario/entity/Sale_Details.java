package com.Micro_Marlet.Inventario.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "sale_details")
public class Sale_Details {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sales sale;  // ← Relación con Sales
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;
    
    @Column(name = "subtotal", nullable = false)
    private Double subtotal;
}