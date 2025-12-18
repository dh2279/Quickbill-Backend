package com.bill.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Item name like Tea, Coffee, Burger
    private String name;

    // Price of item
    private double price;

    // Category like DRINK, FOOD
    private String category;

    // getters & setters
}
