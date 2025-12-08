package org.mol211.ventaceramica.model;

public class SaleDetailsWithProduct {
    Long saleDetailId;
    Long saleId;
    Long productId;
    int quantity;
    double unitPrice;
    double subTotal;
    String productName;
    String productCode;

    public SaleDetailsWithProduct() {
    }

    public Long getSaleDetailId() {
        return saleDetailId;
    }

    public void setSaleDetailId(Long saleDetailId) {
        this.saleDetailId = saleDetailId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public SaleDetailsWithProduct(Long saleDetailId, Long saleId, Long productId, int quantity, double unitPrice, double subTotal, String productName, String productCode) {
        this.saleDetailId = saleDetailId;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
        this.productName = productName;
        this.productCode = productCode;
    }
}
