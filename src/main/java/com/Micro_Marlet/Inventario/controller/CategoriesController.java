package com.Micro_Marlet.Inventario.controller;

import com.Micro_Marlet.Inventario.DTO.CategoriesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.CategoriesResponseDTO;
import com.Micro_Marlet.Inventario.service.CategoriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriesResponseDTO>> getAll() {
        return ResponseEntity.ok(categoriesService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriesService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriesResponseDTO> create(@RequestBody CategoriesRequestDTO request) {
        CategoriesResponseDTO created = categoriesService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CategoriesRequestDTO request) {
        return ResponseEntity.ok(categoriesService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriesService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
