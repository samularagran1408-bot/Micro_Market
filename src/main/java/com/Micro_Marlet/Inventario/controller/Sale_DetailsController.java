package com.Micro_Marlet.Inventario.controller;

import java.util.List;

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
import com.Micro_Marlet.Inventario.service.Sale_DetailsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sale-details")
@RequiredArgsConstructor
public class Sale_DetailsController {

    private final Sale_DetailsService saleDetailsService;

    @GetMapping
    public List<Sale_DetailsResponseDTO> list() {
        return saleDetailsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale_DetailsResponseDTO> get(@PathVariable Long id) {
        return saleDetailsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sale_DetailsResponseDTO> create(@Valid @RequestBody Sale_DetailsRequestDTO body) {
        Sale_DetailsResponseDTO saved = saleDetailsService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale_DetailsResponseDTO> update(@PathVariable Long id, @Valid @RequestBody Sale_DetailsRequestDTO body) {
        return saleDetailsService.update(id, body)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!saleDetailsService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
