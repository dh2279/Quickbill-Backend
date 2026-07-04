package com.bill.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bill.entities.Bill;
import com.bill.entities.BillItem;
import com.bill.entities.Product;
import com.bill.repo.BillRepository;
import com.bill.repo.ProductRepository;

@Service
public class BillService
{

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductRepository productRepository;

	public Bill saveBill(Bill bill)
	{

		double total = 0;

		for (BillItem item : bill.getItems())
		{

			Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			item.setProductName(product.getName());
			item.setPrice(product.getPrice());
			item.setTotal(product.getPrice() * item.getQuantity());

			total += item.getTotal();
		}

		bill.setTotalAmount(total);

		// India Time Zone
		bill.setBillDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));

		return billRepository.save(bill);
	}

	public Bill getBillById(Long id)
	{
		return billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found"));
	}
	
	
	public List<Bill> getAllBills()
	{
	    return billRepository.findAll();
	}
}