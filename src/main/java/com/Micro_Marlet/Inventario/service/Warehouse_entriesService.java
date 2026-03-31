package com.Micro_Marlet.Inventario.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Micro_Marlet.Inventario.DTO.Warehouse_entriesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.Warehouse_entriesResponseDTO;
import com.Micro_Marlet.Inventario.entity.Warehouse_entries;
import com.Micro_Marlet.Inventario.entity.Products;
import com.Micro_Marlet.Inventario.entity.Suppliers;
import com.Micro_Marlet.Inventario.repository.Warehouse_entriesRepository;
import com.Micro_Marlet.Inventario.exception.ResourceNotFoundException;
import com.Micro_Marlet.Inventario.repository.ProductsRepository;
import com.Micro_Marlet.Inventario.repository.SuppliersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Warehouse_entriesService {
    private final Warehouse_entriesRepository warehouseEntriesRepository;
    private final ProductsRepository productsRepository;
    private final SuppliersRepository suppliersRepository;

    // Entrada al almacén
    @Transactional
    public Warehouse_entriesResponseDTO registerEntry(Warehouse_entriesRequestDTO requestDTO) {
        // Buscar el producto por ID
        Products product = productsRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Buscar el proveedor por NIT
        Suppliers supplier = suppliersRepository.findById(requestDTO.getSupplierNit())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedores no encontrado"));

        // Crear la entrada al almacén
        Warehouse_entries warehouseEntry = new Warehouse_entries();
        warehouseEntry.setQuantity(requestDTO.getQuantity());
        warehouseEntry.setEntryDate(requestDTO.getEntryDate());
        warehouseEntry.setProduct(product);
        warehouseEntry.setSupplier(supplier);

        // Guardar la entrada
        warehouseEntriesRepository.save(warehouseEntry);

        // Devolver la respuesta
        return maptoResponseDTO(warehouseEntry);
    }

    @Transactional(readOnly = true)
    public List<Warehouse_entriesResponseDTO> getAllEntries() {
        return warehouseEntriesRepository.findAll().stream()
                .map(this::maptoResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Warehouse_entriesResponseDTO getEntryById(Long id) {
        Warehouse_entries warehouseEntry = warehouseEntriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada al almacén no encontrada"));
        return maptoResponseDTO(warehouseEntry);
    }

    @Transactional
    public void deleteEntry(Long id) {
        Warehouse_entries warehouseEntry = warehouseEntriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada al almacén no encontrada"));

        warehouseEntriesRepository.delete(warehouseEntry);
    }

    public Warehouse_entriesResponseDTO updateEntry(Long id, Warehouse_entriesRequestDTO requestDTO) {
        Warehouse_entries warehouseEntry = warehouseEntriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada al almacén no encontrada"));

        // Actualizar solo los campos que vienen en la request
        if (requestDTO.getQuantity() != null) {
            warehouseEntry.setQuantity(requestDTO.getQuantity());
        }
        if (requestDTO.getEntryDate() != null) {
            warehouseEntry.setEntryDate(requestDTO.getEntryDate());
        }
        if (requestDTO.getProductId() != null) {
            Products product = productsRepository.findById(requestDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
            warehouseEntry.setProduct(product);
        }
        if (requestDTO.getSupplierNit() != null) {
            Suppliers supplier = suppliersRepository.findById(requestDTO.getSupplierNit())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
            warehouseEntry.setSupplier(supplier);
        }

        // Guardar los cambios
        warehouseEntriesRepository.save(warehouseEntry);

        return maptoResponseDTO(warehouseEntry);
    }

    @Transactional(readOnly = true)
    public List<Warehouse_entriesResponseDTO> getEntriesByProductId(Long productId) {
        // Buscamos todas las entradas de ese producto
        List<Warehouse_entries> entries = warehouseEntriesRepository.findByProductId(productId);
        
        if (entries.isEmpty()) {
            throw new ResourceNotFoundException("No hay entradas de almacén para el producto ID: " + productId);
        }
        return entries.stream()
                .map(this::maptoResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<Warehouse_entriesResponseDTO> getEntriesBySupplierNit(Long supplierNit) {
        // Buscamos todas las entradas de ese proveedor
        List<Warehouse_entries> entries = warehouseEntriesRepository.findBySupplierNit(supplierNit);
        
        if (entries.isEmpty()) {
            throw new ResourceNotFoundException("No hay entradas de almacén para el proveedor NIT: " + supplierNit);
        }
        return entries.stream()
                .map(this::maptoResponseDTO)
                .collect(Collectors.toList());
    }

    private Warehouse_entriesResponseDTO maptoResponseDTO(Warehouse_entries warehouseEntry) {
        Warehouse_entriesResponseDTO responseDTO = new Warehouse_entriesResponseDTO();
        responseDTO.setId(warehouseEntry.getId());
        responseDTO.setQuantity(warehouseEntry.getQuantity());
        responseDTO.setEntryDate(warehouseEntry.getEntryDate());
        responseDTO.setProductId(warehouseEntry.getProduct().getId());
        responseDTO.setSupplierNit(warehouseEntry.getSupplier().getNit());
        return responseDTO;
    }
}
