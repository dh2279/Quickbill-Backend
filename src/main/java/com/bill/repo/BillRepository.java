package com.bill.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
