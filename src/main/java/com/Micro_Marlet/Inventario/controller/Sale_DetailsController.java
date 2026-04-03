package com.Micro_Marlet.Inventario.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Micro_Marlet.Inventario.DTO.Sale_DetailsRequestDTO;
import com.Micro_Marlet.Inventario.DTO.Sale_DetailsResponseDTO;

@RestController
@RequestMapping("/api/sale-details")
public class Sale_DetailsController {

    private final Map<Long, Sale_DetailsResponseDTO> store = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @GetMapping
    public List<Sale_DetailsResponseDTO> list() {
        return new ArrayList<>(store.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale_DetailsResponseDTO> get(@PathVariable Long id) {
        Sale_DetailsResponseDTO row = store.get(id);
        if (row == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(row);
    }

    @PostMapping
    public ResponseEntity<Sale_DetailsResponseDTO> create(@RequestBody Sale_DetailsRequestDTO body) {
        if (body.getSaleId() == null || body.getProductId() == null || body.getQuantity() == null || body.getUnitPrice() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (body.getQuantity() < 1 || body.getUnitPrice() < 0) {
            return ResponseEntity.badRequest().build();
        }
        long id = nextId.getAndIncrement();
        double subtotal = body.getQuantity() * body.getUnitPrice();
        Sale_DetailsResponseDTO saved = new Sale_DetailsResponseDTO(
                id,
                body.getSaleId(),
                body.getProductId(),
                body.getQuantity(),
                body.getUnitPrice(),
                subtotal);
        store.put(id, saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale_DetailsResponseDTO> update(@PathVariable Long id, @RequestBody Sale_DetailsRequestDTO body) {
        if (!store.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        if (body.getSaleId() == null || body.getProductId() == null || body.getQuantity() == null || body.getUnitPrice() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (body.getQuantity() < 1 || body.getUnitPrice() < 0) {
            return ResponseEntity.badRequest().build();
        }
        double subtotal = body.getQuantity() * body.getUnitPrice();
        Sale_DetailsResponseDTO updated = new Sale_DetailsResponseDTO(
                id,
                body.getSaleId(),
                body.getProductId(),
                body.getQuantity(),
                body.getUnitPrice(),
                subtotal);
        store.put(id, updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (store.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
