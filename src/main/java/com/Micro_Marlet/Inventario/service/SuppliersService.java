package com.Micro_Marlet.Inventario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Micro_Marlet.Inventario.DTO.SuppliersRequestDTO;
import com.Micro_Marlet.Inventario.entity.Suppliers;
import com.Micro_Marlet.Inventario.DTO.SuppliersResponseDTO;
import com.Micro_Marlet.Inventario.repository.SuppliersRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuppliersService {
    private final SuppliersRepository suppliersRepository;

    public SuppliersResponseDTO createSuplier(SuppliersRequestDTO request) {
        Suppliers supplier = new Suppliers();
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setStatus(request.getStatus());
        suppliersRepository.save(supplier);

        suppliersRepository.save(supplier);

        SuppliersResponseDTO response = new SuppliersResponseDTO();
        response.setNit(supplier.getNit());
        response.setName(supplier.getName());
        response.setPhone(supplier.getPhone());
        response.setEmail(supplier.getEmail());
        response.setAddress(supplier.getAddress());
        response.setStatus(supplier.getStatus());
        return response;
    }

    public List<SuppliersResponseDTO> getAllSuppliers() {
        List<Suppliers> suppliers = suppliersRepository.findAll();
        List<SuppliersResponseDTO> listSuppliers = new ArrayList<>();
        for (Suppliers supplier : suppliers) {
            SuppliersResponseDTO response = new SuppliersResponseDTO();
            response.setNit(supplier.getNit());
            response.setName(supplier.getName());
            response.setPhone(supplier.getPhone());
            response.setEmail(supplier.getEmail());
            response.setAddress(supplier.getAddress());
            response.setStatus(supplier.getStatus());
            listSuppliers.add(response);
        }
        return listSuppliers;
    }

    public SuppliersResponseDTO findByNit(Long nit) {
        Suppliers supplier = suppliersRepository.findByNit(nit)
            .orElseThrow(() -> new IllegalArgumentException("Supplier not found with nit: " + nit));
        SuppliersResponseDTO response = new SuppliersResponseDTO();
        response.setNit(supplier.getNit());
        response.setName(supplier.getName());
        response.setPhone(supplier.getPhone());
        response.setEmail(supplier.getEmail());
        response.setAddress(supplier.getAddress());
        response.setStatus(supplier.getStatus());
        return response;
    }

    public SuppliersResponseDTO updateSupplier(Long nit, SuppliersRequestDTO request) {
        Suppliers supplier = suppliersRepository.findByNit(nit)
            .orElseThrow(() -> new IllegalArgumentException("Supplier not found with nit: " + nit));
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setAddress(request.getAddress());
        supplier.setStatus(request.getStatus());
        suppliersRepository.save(supplier);
        return findByNit(nit);
    }

    public void deleteSupplier(Long nit) {
        Suppliers supplier = suppliersRepository.findByNit(nit)
            .orElseThrow(() -> new IllegalArgumentException("Supplier not found with nit: " + nit));
        suppliersRepository.delete(supplier);
    }
}
