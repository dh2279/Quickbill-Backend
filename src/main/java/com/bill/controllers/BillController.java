package com.bill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.entities.Bill;
import com.bill.services.BillService;
import com.bill.services.PdfService;

@RestController
@RequestMapping("bill/")
@CrossOrigin("http://localhost:5173")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private PdfService pdfService;

    // Create bill
    @PostMapping("create")
    public Bill createBill(@RequestBody Bill bill) {
        return billService.saveBill(bill);
    }

    // Download PDF Invoice
    @GetMapping("invoice/{id}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id) throws Exception {

        Bill bill = billService.getBillById(id);
        byte[] pdf = pdfService.generateInvoice(bill);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

