package com.Micro_Marlet.Inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.Warehouse_entriesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.Warehouse_entriesResponseDTO;
import com.Micro_Marlet.Inventario.service.Warehouse_entriesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouse_entries")
public class Warehouse_entriesController {

    private final Warehouse_entriesService warehouse_entriesService;

    @PostMapping
    public ResponseEntity<Warehouse_entriesResponseDTO> registerEntry (@Valid @RequestBody Warehouse_entriesRequestDTO requestDTO) {
        Warehouse_entriesResponseDTO response = warehouse_entriesService.registerEntry(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Warehouse_entriesResponseDTO>> getAllEntries() {
        List<Warehouse_entriesResponseDTO> entries = warehouse_entriesService.getAllEntries();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse_entriesResponseDTO> getEntryById(@PathVariable Long id) {
        Warehouse_entriesResponseDTO entrie = warehouse_entriesService.getEntryById(id);
        return ResponseEntity.ok(entrie);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        warehouse_entriesService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity <Warehouse_entriesResponseDTO> updateEntry(@PathVariable Long id, @Valid @RequestBody Warehouse_entriesRequestDTO requestDTO) {
        Warehouse_entriesResponseDTO response = warehouse_entriesService.updateEntry(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Warehouse_entriesResponseDTO>> getEntriesByProductId(@PathVariable Long productId) {
        List<Warehouse_entriesResponseDTO> entries = warehouse_entriesService.getEntriesByProductId(productId);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/supplier/{supplierNit}")
    public ResponseEntity<List<Warehouse_entriesResponseDTO>> getEntriesBySupplierNit(@PathVariable Long supplierNit) {
        List<Warehouse_entriesResponseDTO> entries = warehouse_entriesService.getEntriesBySupplierNit(supplierNit);
        return ResponseEntity.ok(entries);
    }
}
