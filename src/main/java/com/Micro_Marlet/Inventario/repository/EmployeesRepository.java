package com.Micro_Marlet.Inventario.repository;

import com.Micro_Marlet.Inventario.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {

    List<Employees> findAllByStatusTrue();

    Optional<Employees> findByIdAndStatusTrue(Long id);

    boolean existsByIdNumber(String idNumber);

    boolean existsByIdNumberAndIdNot(String idNumber, Long id);
}
