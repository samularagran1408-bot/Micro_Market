package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
}
