package com.invoice.invoiceSystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.invoiceSystem.model.Invoice;
import com.invoice.invoiceSystem.model.InvoiceRequest;
import com.invoice.invoiceSystem.model.InvoiceResponse;
import com.invoice.invoiceSystem.model.PaymentRequest;
import com.invoice.invoiceSystem.model.ProcessOverdueRequest;
import com.invoice.invoiceSystem.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
	@Autowired
	private InvoiceService invoiceService;

	@PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        Invoice invoice = invoiceService.createInvoice(invoiceRequest.getAmount(), invoiceRequest.getDueDate());
        return ResponseEntity.status(201).body(new InvoiceResponse(invoice.getId()));
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<Invoice> makePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(invoiceService.makePayment(id, paymentRequest.getAmount()));
    }

    @PostMapping("/process-overdue")
    public ResponseEntity<?> processOverdueInvoices(@RequestBody ProcessOverdueRequest request) {
        invoiceService.processOverdueInvoices(request.getLateFee(), request.getOverdueDays());
        return ResponseEntity.ok().build();
    }
}
