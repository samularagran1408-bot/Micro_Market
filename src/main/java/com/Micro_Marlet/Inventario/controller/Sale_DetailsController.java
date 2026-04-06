package com.Micro_Marlet.Inventario.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.Sale_DetailsRequestDTO;
import com.Micro_Marlet.Inventario.DTO.Sale_DetailsResponseDTO;
import com.Micro_Marlet.Inventario.service.Sale_DetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/sale-details")
@RequiredArgsConstructor
public class Sale_DetailsController {

    private final Sale_DetailsService saleDetailsService;

    @GetMapping
    public ResponseEntity<List<Sale_DetailsResponseDTO>> getAllSaleDetails() {
        return ResponseEntity.ok(saleDetailsService.getAllSaleDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale_DetailsResponseDTO> getSaleDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(saleDetailsService.getSaleDetailById(id));
    }

    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<Sale_DetailsResponseDTO>> getSaleDetailsBySaleId(@PathVariable Long saleId) {
        return ResponseEntity.ok(saleDetailsService.getSaleDetailsBySaleId(saleId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Sale_DetailsResponseDTO>> getSaleDetailsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(saleDetailsService.getSaleDetailsByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<Sale_DetailsResponseDTO> createSaleDetail(@RequestBody Sale_DetailsRequestDTO requestDTO) {
        return ResponseEntity.ok(saleDetailsService.createSaleDetail(requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleDetail(@PathVariable Long id) {
        saleDetailsService.deleteSaleDetail(id);
        return ResponseEntity.noContent().build();
    }
}