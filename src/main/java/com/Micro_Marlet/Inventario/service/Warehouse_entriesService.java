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

    // Entrada al almacén (registrar entrada y actualizar stock)
    @Transactional
    public Warehouse_entriesResponseDTO registerEntry(Warehouse_entriesRequestDTO requestDTO) {
        // Buscar el producto por ID
        Products product = productsRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + requestDTO.getProductId()));

        // Buscar el proveedor por NIT
        Suppliers supplier = suppliersRepository.findById(requestDTO.getSupplierNit())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con NIT: " + requestDTO.getSupplierNit()));

        // Guardar stock anterior
        Integer previousStock = product.getStock();
        
        // Crear la entrada al almacén
        Warehouse_entries warehouseEntry = new Warehouse_entries();
        warehouseEntry.setQuantity(requestDTO.getQuantity());
        warehouseEntry.setEntryDate(requestDTO.getEntryDate());
        warehouseEntry.setProduct(product);
        warehouseEntry.setSupplier(supplier);

        // Guardar la entrada
        Warehouse_entries savedEntry = warehouseEntriesRepository.save(warehouseEntry);
        
        // Actualizar el stock del producto (Regla de negocio)
        Integer newStock = previousStock + requestDTO.getQuantity();
        product.setStock(newStock);
        productsRepository.save(product);

        // Devolver la respuesta con el stock actualizado
        return mapToResponseDTO(savedEntry, newStock);
    }

    // Obtener todas las entradas
    @Transactional(readOnly = true)
    public List<Warehouse_entriesResponseDTO> getAllEntries() {
        return warehouseEntriesRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener entrada por ID
    @Transactional(readOnly = true)
    public Warehouse_entriesResponseDTO getEntryById(Long id) {
        Warehouse_entries warehouseEntry = warehouseEntriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada al almacén no encontrada con ID: " + id));
        return mapToResponseDTO(warehouseEntry);
    }

    // Eliminar entrada (NO actualiza el stock - cuidado con esto)
    @Transactional
    public void deleteEntry(Long id) {
        Warehouse_entries warehouseEntry = warehouseEntriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada al almacén no encontrada con ID: " + id));

        warehouseEntriesRepository.delete(warehouseEntry);
    }

    // Actualizar entrada
    @Transactional
    public Warehouse_entriesResponseDTO updateEntry(Long id, Warehouse_entriesRequestDTO requestDTO) {
        Warehouse_entries warehouseEntry = warehouseEntriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada al almacén no encontrada con ID: " + id));

        // Actualizar solo los campos que vienen en la request
        if (requestDTO.getQuantity() != null) {
            warehouseEntry.setQuantity(requestDTO.getQuantity());
        }
        if (requestDTO.getEntryDate() != null) {
            warehouseEntry.setEntryDate(requestDTO.getEntryDate());
        }
        if (requestDTO.getProductId() != null) {
            Products product = productsRepository.findById(requestDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + requestDTO.getProductId()));
            warehouseEntry.setProduct(product);
        }
        if (requestDTO.getSupplierNit() != null) {
            Suppliers supplier = suppliersRepository.findById(requestDTO.getSupplierNit())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con NIT: " + requestDTO.getSupplierNit()));
            warehouseEntry.setSupplier(supplier);
        }

        // Guardar los cambios
        Warehouse_entries updatedEntry = warehouseEntriesRepository.save(warehouseEntry);

        return mapToResponseDTO(updatedEntry);
    }

    // Obtener entradas por producto ID
    @Transactional(readOnly = true)
    public List<Warehouse_entriesResponseDTO> getEntriesByProductId(Long productId) {
        // Verificar que el producto exista
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));
        
        // Buscar todas las entradas de ese producto
        List<Warehouse_entries> entries = warehouseEntriesRepository.findByProduct(product);
        
        if (entries.isEmpty()) {
            throw new ResourceNotFoundException("No hay entradas de almacén para el producto ID: " + productId);
        }
        
        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener entradas por proveedor NIT
    @Transactional(readOnly = true)
    public List<Warehouse_entriesResponseDTO> getEntriesBySupplierNit(Long supplierNit) {
        // Verificar que el proveedor exista
        Suppliers supplier = suppliersRepository.findById(supplierNit)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con NIT: " + supplierNit));
        
        // Buscar todas las entradas de ese proveedor
        List<Warehouse_entries> entries = warehouseEntriesRepository.findBySupplier(supplier);
        
        if (entries.isEmpty()) {
            throw new ResourceNotFoundException("No hay entradas de almacén para el proveedor NIT: " + supplierNit);
        }
        
        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    
    // Conversión con stock actualizado (para registerEntry)
    private Warehouse_entriesResponseDTO mapToResponseDTO(Warehouse_entries warehouseEntry, Integer updatedStock) {
        Warehouse_entriesResponseDTO responseDTO = mapToResponseDTO(warehouseEntry);
        responseDTO.setUpdatedStock(updatedStock);
        return responseDTO;
    }
    
    // Conversión base (sin stock actualizado)
    private Warehouse_entriesResponseDTO mapToResponseDTO(Warehouse_entries warehouseEntry) {
        Warehouse_entriesResponseDTO responseDTO = new Warehouse_entriesResponseDTO();
        responseDTO.setId(warehouseEntry.getId());
        responseDTO.setQuantity(warehouseEntry.getQuantity());
        responseDTO.setEntryDate(warehouseEntry.getEntryDate());
        
        // Información del producto
        if (warehouseEntry.getProduct() != null) {
            responseDTO.setProductId(warehouseEntry.getProduct().getId());
            responseDTO.setProductName(warehouseEntry.getProduct().getName());
            responseDTO.setProductBarcode(warehouseEntry.getProduct().getBarcode());
        }
        
        // Información del proveedor
        if (warehouseEntry.getSupplier() != null) {
            responseDTO.setSupplierNit(warehouseEntry.getSupplier().getNit());
            responseDTO.setSupplierName(warehouseEntry.getSupplier().getName());
        }
        
        return responseDTO;
    }
}