package com.Micro_Marlet.Inventario.service;

import com.Micro_Marlet.Inventario.DTO.EmployeesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.EmployeesResponseDTO;
import com.Micro_Marlet.Inventario.entity.EmployeeEstado;
import com.Micro_Marlet.Inventario.entity.Employees;
import com.Micro_Marlet.Inventario.repository.EmployeesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        return employeesRepository.findAllByEstado(EmployeeEstado.activo)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EmployeesResponseDTO getById(Long id) {
        Employees entity = employeesRepository.findByIdAndEstado(id, EmployeeEstado.activo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        return toResponse(entity);
    }

    @Transactional
    public EmployeesResponseDTO create(EmployeesRequestDTO request) {
        if (!StringUtils.hasText(request.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
        if (employeesRepository.existsByIdNumber(request.getIdNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un empleado con ese número de identificación");
        }
        if (employeesRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un empleado con ese email");
        }
        Employees entity = new Employees();
        applyRequest(entity, request, true);
        entity.setFechaRegistro(LocalDateTime.now());
        entity.setEstado(EmployeeEstado.activo);
        return toResponse(employeesRepository.save(entity));
    }

    @Transactional
    public EmployeesResponseDTO update(Long id, EmployeesRequestDTO request) {
        Employees entity = employeesRepository.findByIdAndEstado(id, EmployeeEstado.activo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        if (!Objects.equals(entity.getIdNumber(), request.getIdNumber())
                && employeesRepository.existsByIdNumberAndIdNot(request.getIdNumber(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un empleado con ese número de identificación");
        }
        if (!Objects.equals(entity.getEmail(), request.getEmail())
                && employeesRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un empleado con ese email");
        }
        applyRequest(entity, request, false);
        return toResponse(employeesRepository.save(entity));
    }

    @Transactional
    public void softDelete(Long id) {
        Employees entity = employeesRepository.findByIdAndEstado(id, EmployeeEstado.activo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
        entity.setEstado(EmployeeEstado.inactivo);
        employeesRepository.save(entity);
    }

    private void applyRequest(Employees entity, EmployeesRequestDTO request, boolean isCreate) {
        entity.setIdNumber(request.getIdNumber());
        entity.setNombre(request.getNombre());
        entity.setEmail(request.getEmail());
        entity.setTelefono(request.getTelefono());
        entity.setFotoPerfil(request.getFotoPerfil());
        entity.setPosition(request.getPosition());
        entity.setHireDate(request.getHireDate());
        entity.setSalary(request.getSalary());
        if (isCreate || StringUtils.hasText(request.getPasswordHash())) {
            entity.setPasswordHash(request.getPasswordHash());
        }
    }

    private EmployeesResponseDTO toResponse(Employees entity) {
        EmployeesResponseDTO dto = new EmployeesResponseDTO();
        dto.setId(entity.getId());
        dto.setIdNumber(entity.getIdNumber());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setUltimoAcceso(entity.getUltimoAcceso());
        dto.setEstado(entity.getEstado());
        dto.setFotoPerfil(entity.getFotoPerfil());
        dto.setPosition(entity.getPosition());
        dto.setHireDate(entity.getHireDate());
        dto.setSalary(entity.getSalary());
        return dto;
    }
}
