package com.Micro_Marlet.Inventario.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.EmployeesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.EmployeesResponseDTO;
import com.Micro_Marlet.Inventario.service.EmployeesService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeesController {
    
    private final EmployeesService employeesService;
    
    @PostMapping
    public ResponseEntity<EmployeesResponseDTO> createEmployee(
            @Valid @RequestBody EmployeesRequestDTO request) {
        EmployeesResponseDTO response = employeesService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<EmployeesResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeesService.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeesService.getEmployeeById(id));
    }
    
    @GetMapping("/id-number/{idNumber}")
    public ResponseEntity<EmployeesResponseDTO> getEmployeeByIdNumber(@PathVariable String idNumber) {
        return ResponseEntity.ok(employeesService.getEmployeeByIdNumber(idNumber));
    }
    
    @GetMapping("/position/{position}")
    public ResponseEntity<List<EmployeesResponseDTO>> getEmployeesByPosition(
            @PathVariable String position) {
        return ResponseEntity.ok(employeesService.getEmployeesByPosition(position));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<EmployeesResponseDTO>> getEmployeesByHireDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(employeesService.getEmployeesByHireDateRange(start, end));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeesRequestDTO request) {
        EmployeesResponseDTO response = employeesService.updateEmployee(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeesService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activate")
    public ResponseEntity<EmployeesResponseDTO> activateEmployee(@PathVariable Long id) {
        EmployeesResponseDTO response = employeesService.activateEmployee(id);
        return ResponseEntity.ok(response);
    }
}