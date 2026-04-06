package com.Micro_Marlet.Inventario.service;

import com.Micro_Marlet.Inventario.DTO.Sale_DetailsRequestDTO;
import com.Micro_Marlet.Inventario.DTO.Sale_DetailsResponseDTO;
import com.Micro_Marlet.Inventario.DTO.SalesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.SalesResponseDTO;
import com.Micro_Marlet.Inventario.entity.Employees;
import com.Micro_Marlet.Inventario.entity.Products;
import com.Micro_Marlet.Inventario.entity.Sale_Details;
import com.Micro_Marlet.Inventario.entity.Sales;
import com.Micro_Marlet.Inventario.exception.ResourceNotFoundException;
import com.Micro_Marlet.Inventario.repository.EmployeesRepository;
import com.Micro_Marlet.Inventario.repository.ProductsRepository;
import com.Micro_Marlet.Inventario.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final EmployeesRepository employeesRepository;
    private final ProductsRepository productsRepository;

    private static final BigDecimal TAX_RATE = new BigDecimal("0.19");

    @Transactional
    public SalesResponseDTO createSale(SalesRequestDTO request) {
        
        // Buscar empleado
        Employees employee = employeesRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + request.getEmployeeId()));
        
        // Crear venta
        Sales sale = new Sales();
        sale.setEmployee(employee);
        sale.setDate(request.getDate() != null ? request.getDate() : LocalDateTime.now());
        
        BigDecimal subtotal = BigDecimal.ZERO;
        
        // Procesar cada detalle
        for (Sale_DetailsRequestDTO detailDTO : request.getDetails()) {
            
            Products product = productsRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + detailDTO.getProductId()));
            
            // Validar stock
            if (product.getStock() < detailDTO.getQuantity()) {
                throw new IllegalArgumentException(
                    "Stock insuficiente para: " + product.getName() + 
                    ". Disponible: " + product.getStock() + 
                    ", Solicitado: " + detailDTO.getQuantity()
                );
            }
            
            // Calcular subtotal del detalle
            BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal lineSubtotal = unitPrice.multiply(BigDecimal.valueOf(detailDTO.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);
            
            // Crear detalle
            Sale_Details detail = new Sale_Details();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(detailDTO.getQuantity());
            detail.setUnitPrice(unitPrice);
            detail.setSubtotal(lineSubtotal);
            
            sale.getSaleDetails().add(detail);
            
            // Restar stock
            product.setStock(product.getStock() - detailDTO.getQuantity());
            productsRepository.save(product);
            
            // Acumular subtotal
            subtotal = subtotal.add(lineSubtotal);
        }
        
        // Calcular totales
        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);
        
        sale.setSubtotal(subtotal);
        sale.setTax(tax);
        sale.setTotal(total);
        
        // Guardar venta
        Sales savedSale = salesRepository.save(sale);
        
        return mapToResponseDTO(savedSale);
    }
    
    @Transactional(readOnly = true)
    public List<SalesResponseDTO> getAllSales() {
        return salesRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public SalesResponseDTO getSaleById(Long id) {
        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        return mapToResponseDTO(sale);
    }
    
    @Transactional(readOnly = true)
    public List<SalesResponseDTO> getSalesByEmployee(Long employeeId) {
        return salesRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<SalesResponseDTO> getSalesByDateRange(LocalDateTime start, LocalDateTime end) {
        return salesRepository.findByDateBetween(start, end).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    private SalesResponseDTO mapToResponseDTO(Sales sale) {
        SalesResponseDTO response = new SalesResponseDTO();
        response.setId(sale.getId());
        response.setDate(sale.getDate());
        response.setSubtotal(sale.getSubtotal());
        response.setTax(sale.getTax());
        response.setTotal(sale.getTotal());
        response.setEmployeeId(sale.getEmployee().getId());
        response.setEmployeeName(sale.getEmployee().getFullName());
        
        if (sale.getSaleDetails() != null) {
            response.setDetails(sale.getSaleDetails().stream()
                    .map(this::mapDetailToResponseDTO)
                    .collect(Collectors.toList()));
        }
        
        return response;
    }
    
    private Sale_DetailsResponseDTO mapDetailToResponseDTO(Sale_Details detail) {
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