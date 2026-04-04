package com.Micro_Marlet.Inventario.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "sales")
public class Sales {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sale_date")
    private LocalDateTime saleDate = LocalDateTime.now();
    
    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    @Column(name = "tax", precision = 10, scale = 2)
    private BigDecimal tax;
    
    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employees employee;
    
    // Inicializar la lista para evitar NullPointerException
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale_Details> sale_Details = new ArrayList<>();
}