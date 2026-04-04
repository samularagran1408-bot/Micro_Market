package com.Micro_Marlet.Inventario.controller;
//
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.SalesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.SalesResponseDTO;
import com.Micro_Marlet.Inventario.service.SalesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//terminado
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales")
public class SalesController {
    
    private final SalesService salesService;

    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(
            @Valid @RequestBody SalesRequestDTO request) {
        SalesResponseDTO response = salesService.createSale(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {
        List<SalesResponseDTO> sales = salesService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> findById(@PathVariable @NonNull Long id) {
        SalesResponseDTO sale = salesService.getSaleById(id);
        return ResponseEntity.ok(sale);
    }
}
