package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    List<Categories> findAllByStatusTrue();

    Optional<Categories> findByIdAndStatusTrue(Long id);
}
