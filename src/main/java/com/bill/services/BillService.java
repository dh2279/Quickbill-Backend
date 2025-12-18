package com.bill.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bill.entities.*;
import com.bill.repo.*;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductRepository productRepository;

	public Bill saveBill(Bill bill) {

		double total = 0;

		for (BillItem item : bill.getItems()) {

			Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));
			item.setProductName(product.getName());
			item.setPrice(product.getPrice());
			item.setTotal(product.getPrice() * item.getQuantity());

			total += item.getTotal();
		}

		bill.setTotalAmount(total);
		bill.setBillDate(LocalDateTime.now());

		return billRepository.save(bill);
	}

	public Bill getBillById(Long id) {
		return billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found"));
	}
}
