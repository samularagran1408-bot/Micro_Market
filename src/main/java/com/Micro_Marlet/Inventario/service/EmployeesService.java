package com.Micro_Marlet.Inventario.service;

import com.Micro_Marlet.Inventario.DTO.EmployeesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.EmployeesResponseDTO;
import com.Micro_Marlet.Inventario.entity.Employees;
import com.Micro_Marlet.Inventario.repository.EmployeesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public List<EmployeesResponseDTO> getAllActive() {
        return employeesRepository.findAllByStatusTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EmployeesResponseDTO getById(Long id) {
        Employees entity = employeesRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        return toResponse(entity);
    }

    @Transactional
    public EmployeesResponseDTO create(EmployeesRequestDTO request) {
        if (employeesRepository.existsByIdNumber(request.getIdNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un empleado con ese número de identificación");
        }
        Employees entity = new Employees();
        applyRequest(entity, request);
        entity.setStatus(true);
        return toResponse(employeesRepository.save(entity));
    }

    @Transactional
    public EmployeesResponseDTO update(Long id, EmployeesRequestDTO request) {
        Employees entity = employeesRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        if (!Objects.equals(entity.getIdNumber(), request.getIdNumber())
                && employeesRepository.existsByIdNumberAndIdNot(request.getIdNumber(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un empleado con ese número de identificación");
        }
        applyRequest(entity, request);
        return toResponse(employeesRepository.save(entity));
    }

    @Transactional
    public void softDelete(Long id) {
        Employees entity = employeesRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        entity.setStatus(false);
        employeesRepository.save(entity);
    }

    private void applyRequest(Employees entity, EmployeesRequestDTO request) {
        entity.setIdNumber(request.getIdNumber());
        entity.setFullName(request.getFullName());
        entity.setPosition(request.getPosition());
        entity.setHireDate(request.getHireDate());
        entity.setSalary(request.getSalary());
    }

    private EmployeesResponseDTO toResponse(Employees entity) {
        EmployeesResponseDTO dto = new EmployeesResponseDTO();
        dto.setId(entity.getId());
        dto.setIdNumber(entity.getIdNumber());
        dto.setFullName(entity.getFullName());
        dto.setPosition(entity.getPosition());
        dto.setHireDate(entity.getHireDate());
        dto.setSalary(entity.getSalary());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
