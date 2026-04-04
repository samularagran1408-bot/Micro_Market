package com.Micro_Marlet.Inventario.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "saleId", "productId", "quantity", "unitPrice", "subtotal" })
public class Sale_DetailsResponseDTO {

    private Long id;
    private Long saleId;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;

    public Sale_DetailsResponseDTO() {
    }

    public Sale_DetailsResponseDTO(Long id, Long saleId, Long productId, Integer quantity, Double unitPrice, Double subtotal) {
        this.id = id;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
