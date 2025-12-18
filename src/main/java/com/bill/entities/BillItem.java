package com.bill.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product name snapshot
    private String productName;
    
    private Long productId;

    // Quantity ordered
    private int quantity;

    // Price per item
    private double price;

    // Total = quantity * price
    private double total;

    // getters & setters
}

