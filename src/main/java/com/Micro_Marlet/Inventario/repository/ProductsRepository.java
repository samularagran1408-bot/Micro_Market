package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Products;

import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findByBarcode(String barcode);
    
    boolean existsByBarcode(String barcode);

    // Método para buscar productos activos
    List<Products> findByStatusTrue();
    
}
