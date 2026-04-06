package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Employees;
import com.Micro_Marlet.Inventario.entity.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    
    Optional<Employees> findByIdNumber(String idNumber);
    
    boolean existsByIdNumber(String idNumber);
    
    List<Employees> findByPosition(EmployeePosition position);
    
    List<Employees> findByHireDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Employees> findByStatusTrue();
}