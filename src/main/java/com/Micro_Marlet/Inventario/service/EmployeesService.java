package com.Micro_Marlet.Inventario.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Micro_Marlet.Inventario.DTO.EmployeesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.EmployeesResponseDTO;
import com.Micro_Marlet.Inventario.entity.Employees;
import com.Micro_Marlet.Inventario.entity.EmployeePosition;
import com.Micro_Marlet.Inventario.exception.ResourceNotFoundException;
import com.Micro_Marlet.Inventario.repository.EmployeesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeesService {
    
    private final EmployeesRepository employeesRepository;

    @Transactional
    public EmployeesResponseDTO createEmployee(EmployeesRequestDTO request) {
        
        // Validar cédula única
        if (employeesRepository.existsByIdNumber(request.getIdNumber())) {
            throw new IllegalArgumentException("Ya existe un empleado con la cédula: " + request.getIdNumber());
        }
        
        // Validar cargo
        EmployeePosition position;
        try {
            position = EmployeePosition.valueOf(request.getPosition());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cargo inválido. Los cargos permitidos son: ADMINISTRATOR, CASHIER, ASSISTANT");
        }
        
        // Crear empleado
        Employees employee = new Employees();
        employee.setIdNumber(request.getIdNumber());
        employee.setFullName(request.getFullName());
        employee.setPosition(position);
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());
        employee.setStatus(request.getStatus() != null ? request.getStatus() : true);
        
        Employees savedEmployee = employeesRepository.save(employee);
        return mapToResponseDTO(savedEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeesResponseDTO> getAllEmployees() {
        return employeesRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeesResponseDTO getEmployeeById(Long id) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", id));
        return mapToResponseDTO(employee);
    }

    @Transactional(readOnly = true)
    public EmployeesResponseDTO getEmployeeByIdNumber(String idNumber) {
        Employees employee = employeesRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado con cédula"));
        return mapToResponseDTO(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeesResponseDTO> getEmployeesByPosition(String position) {
        EmployeePosition empPosition = EmployeePosition.valueOf(position);
        return employeesRepository.findByPosition(empPosition).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeesResponseDTO> getEmployeesByHireDateRange(LocalDate startDate, LocalDate endDate) {
        return employeesRepository.findByHireDateBetween(startDate, endDate).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeesResponseDTO updateEmployee(Long id, EmployeesRequestDTO request) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", id));
        
        // Validar cédula única si cambió
        if (!employee.getIdNumber().equals(request.getIdNumber()) && 
            employeesRepository.existsByIdNumber(request.getIdNumber())) {
            throw new IllegalArgumentException("Ya existe otro empleado con la cédula: " + request.getIdNumber());
        }
        
        // Validar cargo
        EmployeePosition position = EmployeePosition.valueOf(request.getPosition());
        
        // Actualizar campos
        employee.setIdNumber(request.getIdNumber());
        employee.setFullName(request.getFullName());
        employee.setPosition(position);
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());
        if (request.getStatus() != null) {
            employee.setStatus(request.getStatus());
        }
        
        Employees updatedEmployee = employeesRepository.save(employee);
        return mapToResponseDTO(updatedEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", id));
        
        employee.setStatus(false);  // Soft delete
        employeesRepository.save(employee);
    }

    @Transactional
    public EmployeesResponseDTO activateEmployee(Long id) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", id));
        
        employee.setStatus(true);
        Employees activatedEmployee = employeesRepository.save(employee);
        return mapToResponseDTO(activatedEmployee);
    }

    private EmployeesResponseDTO mapToResponseDTO(Employees employee) {
        EmployeesResponseDTO response = new EmployeesResponseDTO();
        response.setId(employee.getId());
        response.setIdNumber(employee.getIdNumber());
        response.setFullName(employee.getFullName());
        response.setPosition(employee.getPosition().name());
        response.setHireDate(employee.getHireDate());
        response.setSalary(employee.getSalary());
        response.setStatus(employee.getStatus());
        return response;
    }
}