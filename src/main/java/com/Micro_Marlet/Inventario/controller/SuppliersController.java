package com.Micro_Marlet.Inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.SuppliersRequestDTO;
import com.Micro_Marlet.Inventario.DTO.SuppliersResponseDTO;
import com.Micro_Marlet.Inventario.service.SuppliersService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SuppliersController {

    private final SuppliersService suppliersService;

    @PostMapping
    public ResponseEntity<SuppliersResponseDTO> createSupplier(
            @Valid @RequestBody SuppliersRequestDTO request) {
        SuppliersResponseDTO response = suppliersService.createSupplier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SuppliersResponseDTO>> getAllSuppliers() {
        List<SuppliersResponseDTO> suppliers = suppliersService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{nit}")
    public ResponseEntity<SuppliersResponseDTO> findByNit(@PathVariable Long nit) {
        SuppliersResponseDTO supplier = suppliersService.findByNit(nit);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/{nit}")
    public ResponseEntity<SuppliersResponseDTO> updateSupplier(@PathVariable Long nit, @Valid @RequestBody SuppliersRequestDTO request) {
        SuppliersResponseDTO response = suppliersService.updateSupplier(nit, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long nit) {
        suppliersService.deleteSupplier(nit);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{nit}/activate")
    public ResponseEntity<SuppliersResponseDTO> activateSupplier(@PathVariable Long nit) {
        SuppliersResponseDTO response = suppliersService.activateSupplier(nit);
        return ResponseEntity.ok(response);
    }
}