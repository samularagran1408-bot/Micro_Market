package com.Micro_Marlet.Inventario.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "employees")
public class Employees {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_number", unique = true, nullable = false, length = 20)
    private String idNumber;
    
    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private EmployeePosition position;
    
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;
    
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;
    
    @Column(name = "status")
    private Boolean status = true;
}