package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Long> {
    Optional<Suppliers> findByNit(Long nit);

    List<Suppliers> findByStatusTrue();

    // Método para encontrar por email (opcional, si quieres validar unicidad)
    Suppliers findByEmail(String email);
    
}
