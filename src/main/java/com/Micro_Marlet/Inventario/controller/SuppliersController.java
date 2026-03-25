package com.Micro_Marlet.Inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.SuppliersRequestDTO;
import com.Micro_Marlet.Inventario.DTO.SuppliersResponseDTO;
import com.Micro_Marlet.Inventario.service.SuppliersService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/suppliers")
public class SuppliersController {

    private final SuppliersService suppliersService;

    @PostMapping
    public ResponseEntity<SuppliersResponseDTO> createSupplier(@RequestBody SuppliersRequestDTO request) {
        try {
            SuppliersResponseDTO response = suppliersService.createSuplier(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<SuppliersResponseDTO>> getAllSuppliers() {
        try {
            List<SuppliersResponseDTO> suppliers = suppliersService.getAllSuppliers();
            return ResponseEntity.status(HttpStatus.OK).body(suppliers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuppliersResponseDTO> findByNit(@PathVariable Long nit) {
        try {
            SuppliersResponseDTO supplier = suppliersService.findByNit(nit);
            return ResponseEntity.status(HttpStatus.OK).body(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long nit) {
        try {
            suppliersService.deleteSupplier(nit);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuppliersResponseDTO> updateSupplier(@PathVariable Long nit, @RequestBody SuppliersRequestDTO request) {
        try {
            SuppliersResponseDTO response = suppliersService.updateSupplier(nit, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
