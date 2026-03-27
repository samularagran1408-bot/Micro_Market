package com.Micro_Marlet.Inventario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Micro_Marlet.Inventario.DTO.ProductsRequestDTO;
import com.Micro_Marlet.Inventario.entity.Categories;
import com.Micro_Marlet.Inventario.entity.Products;
import com.Micro_Marlet.Inventario.DTO.ProductsResponseDTO;
import com.Micro_Marlet.Inventario.repository.CategoriesRepository;
import com.Micro_Marlet.Inventario.repository.ProductsRepository;
import com.Micro_Marlet.Inventario.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;

    @Transactional
    public ProductsResponseDTO createProduct(ProductsRequestDTO request) {
        
        // Validar que no exista un producto con el mismo código de barras
        if (productsRepository.existsByBarcode(request.getBarcode())) {
            throw new IllegalArgumentException(
                "Ya existe un producto con el código de barras: " + request.getBarcode()
            );
        }
        
        Categories category = categoriesRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.getCategoryId()));
        
        Products product = new Products();
        product.setBarcode(request.getBarcode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock() != null ? request.getStock() : 0);
        product.setStatus(request.getStatus() != null ? request.getStatus() : true);
        product.setCategory(category);
        
        Products savedProduct = productsRepository.save(product);
        return mapToResponseDTO(savedProduct);
    }
    
    @Transactional(readOnly = true)
    public List<ProductsResponseDTO> getAllProducts() {
        return productsRepository.findAll().stream()
            .filter(product -> product.getStatus() != null)
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ProductsResponseDTO getProductById(Long id) {
        Products product = productsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        return mapToResponseDTO(product);
    }
    
    @Transactional(readOnly = true)
    public ProductsResponseDTO getProductByBarcode(String barcode) {
        Products product = productsRepository.findByBarcode(barcode)
            .orElseThrow(() -> new ResourceNotFoundException("Producto con código de barras"));
        return mapToResponseDTO(product);
    }
    
    @Transactional
    public ProductsResponseDTO updateProduct(Long id, ProductsRequestDTO request) {
        Products product = productsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        
        // Si cambia el código de barras, validar que no exista otro con ese código
        if (!product.getBarcode().equals(request.getBarcode()) && 
            productsRepository.existsByBarcode(request.getBarcode())) {
            throw new IllegalArgumentException(
                "Ya existe otro producto con el código de barras: " + request.getBarcode()
            );
        }
        
        // Si cambia la categoría, buscar la nueva
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            Categories category = categoriesRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.getCategoryId()));
            product.setCategory(category);
        }
        
        // Actualizar campos
        product.setBarcode(request.getBarcode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
        }
        
        Products updatedProduct = productsRepository.save(product);
        return mapToResponseDTO(updatedProduct);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Products product = productsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        
        product.setStatus(false);  // Soft delete
        productsRepository.save(product);
    }
    
    @Transactional
    public ProductsResponseDTO activateProduct(Long id) {
        Products product = productsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        
        product.setStatus(true);
        Products activatedProduct = productsRepository.save(product);
        return mapToResponseDTO(activatedProduct);
    }
    
    // Método privado para convertir Entity a DTO
    private ProductsResponseDTO mapToResponseDTO(Products product) {
        ProductsResponseDTO response = new ProductsResponseDTO();
        response.setId(product.getId());
        response.setBarcode(product.getBarcode());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setStatus(product.getStatus());
        
        // Incluir información de la categoría
        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());  // Si tienes este campo en DTO
        }
        
        return response;
    }
}