package org.mol211.ventaceramica.model;

import java.time.LocalDateTime;

public class Customer {
    private Long id;
    private String name;
    private String mail;
    private String phone;
    private LocalDateTime createdAt;

    public Customer() {
    }

    public Customer(Long id, String name, String mail, String phone, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
