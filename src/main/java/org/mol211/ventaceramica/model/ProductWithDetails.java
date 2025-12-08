package org.mol211.ventaceramica.model;

public class ProductWithDetails {
    Long productId;
    String productName;
    double totalSold;
    double totalRevenue;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(double totalSold) {
        this.totalSold = totalSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public ProductWithDetails() {
    }

    public ProductWithDetails(Long productId, String productName, double totalSold, double totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
        this.totalRevenue = totalRevenue;

    }
}
