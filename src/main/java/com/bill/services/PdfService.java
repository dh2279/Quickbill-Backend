package com.bill.services;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.bill.entities.Bill;
import com.bill.entities.BillItem;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService {

    public byte[] generateInvoice(Bill bill) throws Exception {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Cafe Invoice"));
        document.add(new Paragraph("Bill ID: " + bill.getId()));
        document.add(new Paragraph("Date: " + bill.getBillDate()));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.addCell("Item");
        table.addCell("Qty");
        table.addCell("Price");
        table.addCell("Total");

        for (BillItem item : bill.getItems()) {
            table.addCell(item.getProductName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getPrice()));
            table.addCell(String.valueOf(item.getTotal()));
        }

        document.add(table);
        document.add(new Paragraph("Total Amount: " + bill.getTotalAmount()));

        document.close();
        return out.toByteArray();
    }
}
