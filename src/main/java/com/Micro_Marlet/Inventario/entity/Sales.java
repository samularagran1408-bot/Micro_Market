package com.Micro_Marlet.Inventario.entity;

import lombok.Data;
import jakarta.persistence.*;
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
    
    @Column(name = "subtotal")
    private Double subtotal;
    
    @Column(name = "tax")
    private Double tax;
    
    @Column(name = "total")
    private Double total;
    
    // Corrección: FetchType.LAZY (con mayúscula inicial)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employees employee;
    
    // Corrección: CascadeType.ALL (con mayúscula inicial)
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale_Details> saleDetails = new ArrayList<>();  // ← También usa singular para la clase
}
