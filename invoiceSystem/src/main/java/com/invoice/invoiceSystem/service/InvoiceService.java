package com.invoice.invoiceSystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.invoice.invoiceSystem.model.Invoice;

public interface InvoiceService {

	Invoice createInvoice(Double amount, LocalDate dueDate);

	List<Invoice> getAllInvoices();

	void processOverdueInvoices(Double lateFee, int overdueDays);

	Invoice makePayment(Long id, Double paymentAmount);

	Optional<Invoice> getInvoiceById(Long id);

}
