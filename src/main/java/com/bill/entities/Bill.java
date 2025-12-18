package com.bill.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    // Bill date
    private LocalDateTime billDate;

    // Final amount
    private double totalAmount;

    // One bill has many items
    @OneToMany(cascade = CascadeType.ALL)
    private List<BillItem> items;

    // getters & setters
}

