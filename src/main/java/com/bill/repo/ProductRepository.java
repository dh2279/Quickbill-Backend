package com.bill.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

