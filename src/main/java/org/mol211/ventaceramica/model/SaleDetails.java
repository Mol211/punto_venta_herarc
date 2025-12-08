package org.mol211.ventaceramica.model;

public class SaleDetails {
    private Long id;
    private Long sale_id;
    private Long product_id;
    private Integer quantity;
    private Double unit_price;
    private Double subtotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSale_id() {
        return sale_id;
    }

    public void setSale_id(Long sale_id) {
        this.sale_id = sale_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public SaleDetails() {
    }

    public SaleDetails(Long id, Long sale_id,Long product_id, Integer quantity, Double unit_price, Double subtotal) {
        this.id = id;
        this.sale_id = sale_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.subtotal = subtotal;
    }
}
