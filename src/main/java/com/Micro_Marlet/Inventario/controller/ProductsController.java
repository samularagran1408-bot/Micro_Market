package com.Micro_Marlet.Inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Micro_Marlet.Inventario.DTO.ProductsRequestDTO;
import com.Micro_Marlet.Inventario.DTO.ProductsResponseDTO;
import com.Micro_Marlet.Inventario.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductsController {
    
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductsResponseDTO> createProduct(
            @Valid @RequestBody ProductsRequestDTO request) {
        ProductsResponseDTO response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductsResponseDTO>> getAllProducts() {
        List<ProductsResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> findById(@PathVariable Long id) {
        ProductsResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductsResponseDTO> findByBarcode(@PathVariable String barcode) {
        ProductsResponseDTO product = productService.getProductByBarcode(barcode);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductsRequestDTO request) {
        ProductsResponseDTO response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductsResponseDTO> activateProduct(@PathVariable Long id) {
        ProductsResponseDTO response = productService.activateProduct(id);
        return ResponseEntity.ok(response);
    }
}
