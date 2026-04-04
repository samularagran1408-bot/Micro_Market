package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Products;
import com.Micro_Marlet.Inventario.entity.Suppliers;
import com.Micro_Marlet.Inventario.entity.Warehouse_entries;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;   

@Repository
public interface Warehouse_entriesRepository extends JpaRepository<Warehouse_entries, Long> {

    List<Warehouse_entries> findByProduct(Products product);
    
    List<Warehouse_entries> findBySupplier(Suppliers supplier);
    
}
