package com.Micro_Marlet.Inventario.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductsRequestDTO {

    @NotBlank(message = "El código de barras es obligatorio")
    @Size(max = 100, message = "El código de barras no puede exceder los 100 caracteres")
    private String barcode;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 200, message = "El nombre del producto no puede exceder los 200 caracteres")
    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private Boolean status;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

    public ProductsRequestDTO() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
