package com.Micro_Marlet.Inventario.controller;

import com.Micro_Marlet.Inventario.DTO.EmployeesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.EmployeesResponseDTO;
import com.Micro_Marlet.Inventario.service.EmployeesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeesService employeesService;

    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeesResponseDTO>> getAll() {
        return ResponseEntity.ok(employeesService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeesService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeesResponseDTO> create(@RequestBody EmployeesRequestDTO request) {
        EmployeesResponseDTO created = employeesService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> update(
            @PathVariable Long id,
            @RequestBody EmployeesRequestDTO request) {
        return ResponseEntity.ok(employeesService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeesService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<EmployeesResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(employeesService.activate(id));
    }
}
