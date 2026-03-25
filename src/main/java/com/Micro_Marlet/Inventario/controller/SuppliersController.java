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
    public ResponseEntity<SuppliersResponseDTO> createSupplier(@Valid @RequestBody SuppliersRequestDTO request) {
        try {
            SuppliersResponseDTO response = suppliersService.createSupplier(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<SuppliersResponseDTO>> getAllSuppliers() {
        try {
            List<SuppliersResponseDTO> suppliers = suppliersService.getAllSuppliers();
            return ResponseEntity.ok(suppliers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{nit}")
    public ResponseEntity<SuppliersResponseDTO> findByNit(@PathVariable Long nit) {
        try {
            SuppliersResponseDTO supplier = suppliersService.findByNit(nit);
            return ResponseEntity.ok(supplier);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{nit}")
    public ResponseEntity<SuppliersResponseDTO> updateSupplier(
            @PathVariable Long nit, 
            @Valid @RequestBody SuppliersRequestDTO request) {
        try {
            SuppliersResponseDTO response = suppliersService.updateSupplier(nit, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long nit) {
        try {
            suppliersService.deleteSupplier(nit);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Activar con patch porque solo actualiza un recurso
    @PatchMapping("/{nit}/activate")
    public ResponseEntity<SuppliersResponseDTO> activateSupplier(@PathVariable Long nit) {
        SuppliersResponseDTO response = suppliersService.activateSupplier(nit);
        return ResponseEntity.ok(response);
    }
}