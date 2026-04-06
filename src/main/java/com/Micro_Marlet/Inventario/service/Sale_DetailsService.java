package com.Micro_Marlet.Inventario.service;

import com.Micro_Marlet.Inventario.DTO.Sale_DetailsRequestDTO;
import com.Micro_Marlet.Inventario.DTO.Sale_DetailsResponseDTO;
import com.Micro_Marlet.Inventario.entity.Products;
import com.Micro_Marlet.Inventario.entity.Sale_Details;
import com.Micro_Marlet.Inventario.entity.Sales;
import com.Micro_Marlet.Inventario.exception.ResourceNotFoundException;
import com.Micro_Marlet.Inventario.repository.ProductsRepository;
import com.Micro_Marlet.Inventario.repository.Sale_DetailsRepository;
import com.Micro_Marlet.Inventario.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Sale_DetailsService {

    private final Sale_DetailsRepository saleDetailsRepository;
    private final SalesRepository salesRepository;
    private final ProductsRepository productsRepository;

    @Transactional(readOnly = true)
    public List<Sale_DetailsResponseDTO> getAllSaleDetails() {
        return saleDetailsRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Sale_DetailsResponseDTO getSaleDetailById(Long id) {
        Sale_Details detail = saleDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de venta no encontrado con ID: " + id));
        return mapToResponseDTO(detail);
    }

    @Transactional(readOnly = true)
    public List<Sale_DetailsResponseDTO> getSaleDetailsBySaleId(Long saleId) {
        return saleDetailsRepository.findBySaleId(saleId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Sale_DetailsResponseDTO> getSaleDetailsByProductId(Long productId) {
        return saleDetailsRepository.findByProductId(productId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Sale_DetailsResponseDTO createSaleDetail(Sale_DetailsRequestDTO requestDTO) {
        // Buscar la venta
        Sales sale = salesRepository.findById(requestDTO.getSaleId())
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + requestDTO.getSaleId()));

        // Buscar el producto
        Products product = productsRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + requestDTO.getProductId()));

        // Validar stock
        if (product.getStock() < requestDTO.getQuantity()) {
            throw new IllegalArgumentException("Stock insuficiente para: " + product.getName());
        }

        // Calcular subtotal
        BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(requestDTO.getQuantity()))
                .setScale(2, RoundingMode.HALF_UP);

        // Crear detalle
        Sale_Details detail = new Sale_Details();
        detail.setSale(sale);
        detail.setProduct(product);
        detail.setQuantity(requestDTO.getQuantity());
        detail.setUnitPrice(unitPrice);
        detail.setSubtotal(subtotal);

        // Restar stock
        product.setStock(product.getStock() - requestDTO.getQuantity());
        productsRepository.save(product);

        // Guardar detalle
        Sale_Details savedDetail = saleDetailsRepository.save(detail);

        // Actualizar totales de la venta
        updateSaleTotals(sale);

        return mapToResponseDTO(savedDetail);
    }

    @Transactional
    public void deleteSaleDetail(Long id) {
        Sale_Details detail = saleDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de venta no encontrado con ID: " + id));

        // Devolver stock al producto
        Products product = detail.getProduct();
        product.setStock(product.getStock() + detail.getQuantity());
        productsRepository.save(product);

        // Eliminar detalle
        saleDetailsRepository.delete(detail);

        // Actualizar totales de la venta
        updateSaleTotals(detail.getSale());
    }

    private void updateSaleTotals(Sales sale) {
        BigDecimal subtotal = sale.getSaleDetails().stream()
                .map(Sale_Details::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal tax = subtotal.multiply(new BigDecimal("0.19")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);

        sale.setSubtotal(subtotal);
        sale.setTax(tax);
        sale.setTotal(total);

        salesRepository.save(sale);
    }

    private Sale_DetailsResponseDTO mapToResponseDTO(Sale_Details detail) {
        Sale_DetailsResponseDTO response = new Sale_DetailsResponseDTO();
        response.setId(detail.getId());
        response.setProductId(detail.getProduct().getId());
        response.setProductName(detail.getProduct().getName());
        response.setQuantity(detail.getQuantity());
        response.setUnitPrice(detail.getUnitPrice());
        response.setSubtotal(detail.getSubtotal());
        return response;
    }
}