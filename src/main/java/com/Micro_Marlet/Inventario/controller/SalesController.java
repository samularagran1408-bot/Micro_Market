package com.Micro_Marlet.Inventario.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.SalesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.SalesResponseDTO;
import com.Micro_Marlet.Inventario.service.SalesService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
    
    private final SalesService salesService;
    
    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(@Valid @RequestBody SalesRequestDTO request) {
        SalesResponseDTO response = salesService.createSale(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(salesService.getSalesByEmployee(employeeId));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(salesService.getSalesByDateRange(start, end));
    }
}