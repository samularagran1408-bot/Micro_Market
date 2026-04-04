package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Products;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    List<Products> findAllByCategoryId(Long categoryId);
=======
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findByBarcode(String barcode);
    
    boolean existsByBarcode(String barcode);
    
>>>>>>> 99802a21435ecb4a4d6bbdaa072d2eb522329606
}
