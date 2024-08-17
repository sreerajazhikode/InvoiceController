package com.invoice.invoiceSystem.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.invoiceSystem.model.Invoice;
import com.invoice.invoiceSystem.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
	@Autowired
	private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        Invoice newInvoice = new Invoice();
        newInvoice.setId(createdInvoice.getId());
        return new  ResponseEntity<>( newInvoice, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<Invoice> payInvoice(@PathVariable Long id) {
        Optional<Invoice> paidInvoice = invoiceService.payInvoice(id);
        return paidInvoice.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/process-overdue")
    public ResponseEntity<List<Invoice>> processOverdueInvoices() {
        List<Invoice> overdueInvoices = invoiceService.processOverdueInvoices();
        return ResponseEntity.ok(overdueInvoices);
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        return invoice.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
