package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findByEmployeeId(Long employeeId);
    List<Sales> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}