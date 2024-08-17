package com.invoice.invoiceSystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoice.invoiceSystem.model.Invoice;
import com.invoice.invoiceSystem.repository.InvoiceRepository;
@Component
public class InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> payInvoice(Long id) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setPaid(true);
            invoice.setPaymentDate(LocalDate.now());
            return invoiceRepository.save(invoice);
        });
    }

    public List<Invoice> processOverdueInvoices() {
        List<Invoice> overdueInvoices = invoiceRepository.findByPaidFalseAndDueDateBefore(LocalDate.now());
        overdueInvoices.forEach(invoice -> {
            invoice.setPaid(true); // Processing could mean marking them as paid, with penalty fees, etc.
            invoiceRepository.save(invoice);
        });
        return overdueInvoices;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }
}
