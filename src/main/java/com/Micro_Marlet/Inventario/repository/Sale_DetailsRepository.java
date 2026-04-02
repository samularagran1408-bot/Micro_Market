package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Sale_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Sale_DetailsRepository extends JpaRepository<Sale_Details, Long> {
}
