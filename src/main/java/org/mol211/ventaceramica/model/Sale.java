package org.mol211.ventaceramica.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private LocalDate sale_date;
    private Double totalPrice;
    private PaymentMethod paymentMethod;
    private Long customer_id;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSale_date() {
        return sale_date;
    }

    public void setSale_date(LocalDate sale_date) {
        this.sale_date = sale_date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Sale() {
    }

    public Sale(Long id, LocalDate sale_date, Double totalPrice, PaymentMethod paymentMethod, Long customer_id, LocalDateTime createdAt) {
        this.id = id;
        this.sale_date = sale_date;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.customer_id = customer_id;
        this.createdAt = createdAt;
    }
}
