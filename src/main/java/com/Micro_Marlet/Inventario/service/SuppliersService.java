package com.Micro_Marlet.Inventario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Transactional es para ejcutar dentro de una BD

import com.Micro_Marlet.Inventario.DTO.SuppliersRequestDTO;
import com.Micro_Marlet.Inventario.entity.Suppliers;
import com.Micro_Marlet.Inventario.DTO.SuppliersResponseDTO;
import com.Micro_Marlet.Inventario.repository.SuppliersRepository;
import com.Micro_Marlet.Inventario.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuppliersService {
    
    private final SuppliersRepository suppliersRepository;

    @Transactional
    public SuppliersResponseDTO createSupplier(SuppliersRequestDTO request) {
        // Validar que no exista un proveedor con el mismo email (opcional)
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            Suppliers supplier = suppliersRepository.findByEmail(request.getEmail());
            if (supplier != null) {
                throw new ResourceNotFoundException("Supplier with email already exists: " + supplier.getEmail());
            }
        }
        
        Suppliers supplier = new Suppliers();
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setStatus(request.getStatus() != null ? request.getStatus() : true);
        
        Suppliers savedSupplier = suppliersRepository.save(supplier);
        
        return mapToResponseDTO(savedSupplier);
    }

    @Transactional(readOnly = true)
    public List<SuppliersResponseDTO> getAllSuppliers() {
        // Mostrar solo los proveedores activos (status = true)
        List<Suppliers> suppliers = suppliersRepository.findByStatusTrue();
        return suppliers.stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SuppliersResponseDTO findByNit(Long nit) {
        Suppliers supplier = suppliersRepository.findByNit(nit)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found", nit));
        return mapToResponseDTO(supplier);
    }

    @Transactional
    public SuppliersResponseDTO updateSupplier(Long nit, SuppliersRequestDTO request) {
        Suppliers supplier = suppliersRepository.findByNit(nit)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found", nit));
        
        // Actualizar solo los campos que vienen en la request
        if (request.getName() != null) {
            supplier.setName(request.getName());
        }
        if (request.getPhone() != null) {
            supplier.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            supplier.setEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            supplier.setAddress(request.getAddress());
        }
        if (request.getStatus() != null) {
            supplier.setStatus(request.getStatus());
        }
        
        Suppliers updatedSupplier = suppliersRepository.save(supplier);
        return mapToResponseDTO(updatedSupplier);
    }

    @Transactional
    public void deleteSupplier(Long nit) {
        Suppliers supplier = suppliersRepository.findByNit(nit)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found", nit));
        
        // Soft Delete
        supplier.setStatus(false);
        suppliersRepository.save(supplier);
    }

    @Transactional
    public SuppliersResponseDTO activateSupplier(Long nit) {
        Suppliers supplier = suppliersRepository.findByNit(nit)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found", nit));
        
        supplier.setStatus(true);
        Suppliers activatedSupplier = suppliersRepository.save(supplier);
        return mapToResponseDTO(activatedSupplier);
    }

    // Método privado para convertir Entity a DTO
    private SuppliersResponseDTO mapToResponseDTO(Suppliers supplier) {
        SuppliersResponseDTO response = new SuppliersResponseDTO();
        response.setNit(supplier.getNit());
        response.setName(supplier.getName());
        response.setPhone(supplier.getPhone());
        response.setEmail(supplier.getEmail());
        response.setAddress(supplier.getAddress());
        response.setStatus(supplier.getStatus());
        return response;
    }
}