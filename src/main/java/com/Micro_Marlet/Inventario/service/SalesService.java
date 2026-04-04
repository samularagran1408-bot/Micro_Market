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
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//terminado
@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final EmployeesRepository employeesRepository;
    private final ProductsRepository productsRepository;

    @Transactional
    public SalesResponseDTO createSale(SalesRequestDTO request) {
        Employees employee = employeesRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", request.getEmployeeId()));

        Sales sale = new Sales();
        sale.setEmployee(employee);
        if (request.getDate() != null) {
            sale.setSaleDate(request.getDate());
        } else {
            sale.setSaleDate(LocalDateTime.now());
        }

        for (Sale_DetailsRequestDTO lineDto : request.getDetails()) {
            Products product = productsRepository.findById(lineDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", lineDto.getProductId()));

            if (product.getStatus() == null || !product.getStatus()) {
                throw new IllegalArgumentException("Producto inactivo: " + product.getId());
            }

            int qty = lineDto.getQuantity();
            if (product.getStock() < qty) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para " + product.getName() + " (hay " + product.getStock() + ")");
            }

            BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty)).setScale(2, RoundingMode.HALF_UP);

            Sale_Details line = new Sale_Details();
            line.setSale(sale);
            line.setProduct(product);
            line.setQuantity(qty);
            line.setUnitPrice(unitPrice);
            line.setLineTotal(lineTotal);

            product.setStock(product.getStock() - qty);
            productsRepository.save(product);

            sale.getSale_Details().add(line);
        }

        setTotalsFromLines(sale);

        Sales saved = salesRepository.save(sale);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SalesResponseDTO> getAllSales() {
        List<Sales> sales = salesRepository.findAll();
        List<SalesResponseDTO> list = new ArrayList<>();
        for (Sales s : sales) {
            list.add(mapToResponse(s));
        }
        return list;
    }

    @Transactional(readOnly = true)
    public SalesResponseDTO getSaleById(@NonNull Long id) {
        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta", id));
        return mapToResponse(sale);
    }

    // subtotal = suma de líneas; impuesto 0; total = subtotal (luego se puede cambiar la regla)
    private void setTotalsFromLines(Sales sale) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Sale_Details line : sale.getSale_Details()) {
            subtotal = subtotal.add(line.getLineTotal());
        }
        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        sale.setSubtotal(subtotal);
        sale.setTax(tax);
        sale.setTotal(subtotal.add(tax));
    }

    private SalesResponseDTO mapToResponse(Sales sale) {
        SalesResponseDTO dto = new SalesResponseDTO();
        dto.setId(sale.getId());
        dto.setDate(sale.getSaleDate());
        dto.setTotal(sale.getSubtotal());
        dto.setTax(sale.getTax());
        dto.setSubtotal(sale.getTotal());
        if (sale.getEmployee() != null) {
            dto.setEmployeeId(sale.getEmployee().getId());
            dto.setEmployeeName(sale.getEmployee().getFullName());
        }
        for (Sale_Details line : sale.getSale_Details()) {
            dto.getDetails().add(mapDetailToResponse(line));
        }
        return dto;
    }

    private Sale_DetailsResponseDTO mapDetailToResponse(Sale_Details line) {
        Sale_DetailsResponseDTO d = new Sale_DetailsResponseDTO();
        d.setId(line.getId());
        if (line.getProduct() != null) {
            d.setProductId(line.getProduct().getId());
            d.setProductName(line.getProduct().getName());
        }
        d.setQuantity(line.getQuantity());
        d.setUnitPrice(line.getUnitPrice());
        d.setLineTotal(line.getLineTotal());
        return d;
    }
}
