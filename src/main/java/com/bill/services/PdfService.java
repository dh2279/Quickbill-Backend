package com.bill.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.bill.entities.Bill;
import com.bill.entities.BillItem;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService
{

	public byte[] generateInvoice(Bill bill) throws Exception
	{

		Document document = new Document();

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, out);

		document.open();

		// FONTS
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD, Color.BLACK);

		Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, Color.BLACK);

		Font boldFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, Color.BLACK);

		// TITLE
		Paragraph title = new Paragraph("CAFE BILLING SYSTEM", titleFont);

		title.setAlignment(Element.ALIGN_CENTER);

		document.add(title);

		document.add(new Paragraph(" "));

		// DATE & BILL INFO
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		document.add(new Paragraph("Bill ID : " + bill.getId(), normalFont));

		document.add(new Paragraph("Date : " + now.format(formatter), normalFont));

		document.add(new Paragraph("Cashier : "
				+ (bill.getCashierName() != null && !bill.getCashierName().isEmpty() ? bill.getCashierName() : "N/A"),
				normalFont));

		document.add(new Paragraph("GST : 5%", normalFont));

		document.add(new Paragraph(" "));

		// TABLE
		PdfPTable table = new PdfPTable(4);

		table.setWidthPercentage(100);

		table.setWidths(new int[] { 3, 2, 2, 2 });

		// HEADER
		PdfPCell h1 = new PdfPCell(new Phrase("Product", boldFont));

		PdfPCell h2 = new PdfPCell(new Phrase("Price", boldFont));

		PdfPCell h3 = new PdfPCell(new Phrase("Qty", boldFont));

		PdfPCell h4 = new PdfPCell(new Phrase("Total", boldFont));

		h1.setBackgroundColor(Color.LIGHT_GRAY);

		h2.setBackgroundColor(Color.LIGHT_GRAY);

		h3.setBackgroundColor(Color.LIGHT_GRAY);

		h4.setBackgroundColor(Color.LIGHT_GRAY);

		table.addCell(h1);
		table.addCell(h2);
		table.addCell(h3);
		table.addCell(h4);

		// ITEMS
		double subtotal = 0;

		for (BillItem item : bill.getItems())
		{

			double itemTotal = item.getPrice() * item.getQuantity();

			subtotal += itemTotal;

			table.addCell(item.getProductName());

			table.addCell("Rs. " + item.getPrice());

			table.addCell(String.valueOf(item.getQuantity()));

			table.addCell("Rs. " + itemTotal);
		}

		document.add(table);

		document.add(new Paragraph(" "));

		// GST
		double gstAmount = (subtotal * 5) / 100;

		double grandTotal = subtotal + gstAmount;

		Paragraph subtotalText = new Paragraph("Subtotal : Rs. " + subtotal, boldFont);

		subtotalText.setAlignment(Element.ALIGN_RIGHT);

		Paragraph gstText = new Paragraph("GST (5%) : Rs. " + gstAmount, boldFont);

		gstText.setAlignment(Element.ALIGN_RIGHT);

		Paragraph totalText = new Paragraph("Grand Total : Rs. " + grandTotal, boldFont);

		totalText.setAlignment(Element.ALIGN_RIGHT);

		document.add(subtotalText);

		document.add(gstText);

		document.add(totalText);

		document.add(new Paragraph(" "));

		// FOOTER
		Paragraph footer = new Paragraph("Thank You Visit Again ☕", boldFont);

		footer.setAlignment(Element.ALIGN_CENTER);

		document.add(footer);

		document.close();

		return out.toByteArray();
	}
}