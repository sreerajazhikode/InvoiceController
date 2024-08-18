package com.invoice.invoiceSystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoice.invoiceSystem.model.Invoice;
import com.invoice.invoiceSystem.repository.InvoiceRepository;
@Component
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Override
	public Invoice createInvoice(Double amount, LocalDate dueDate) {
		Invoice invoice = new Invoice();
		invoice.setAmount(amount);
		invoice.setDueDate(dueDate);
		return invoiceRepository.save(invoice);
	}
	@Override
	public List<Invoice> getAllInvoices() {
		return invoiceRepository.findAll();
	}
	@Override
	public Optional<Invoice> getInvoiceById(Long id) {
		return invoiceRepository.findById(id);
	}
	@Override
	public Invoice makePayment(Long id, Double paymentAmount) {
		Invoice invoice = invoiceRepository.findById(id).orElseThrow();
		invoice.setPaidAmount(invoice.getPaidAmount() + paymentAmount);

		if (invoice.getPaidAmount() >= invoice.getAmount()) {
			invoice.setStatus("paid");
		}
		return invoiceRepository.save(invoice);
	}
	@Override
	public List<Invoice> processOverdueInvoices(Double lateFee, int overdueDays) {
		List<Invoice> invoices = invoiceRepository.findAll();
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		for (Invoice invoice : invoices) {
			if ("pending".equals(invoice.getStatus()) && invoice.getDueDate().plusDays(overdueDays).isBefore(LocalDate.now())) {
				if (invoice.getPaidAmount() > 0) {
					invoice.setStatus("paid");
					Invoice newInvoice = new Invoice();
					newInvoice.setAmount(invoice.getAmount() - invoice.getPaidAmount() + lateFee);
					newInvoice.setDueDate(LocalDate.now().plusDays(overdueDays));
					invoiceRepository.save(newInvoice);
				} else {
					invoice.setStatus("void");
					Invoice newInvoice = new Invoice();
					newInvoice.setAmount(invoice.getAmount() + lateFee);
					newInvoice.setDueDate(LocalDate.now().plusDays(overdueDays));
					invoiceRepository.save(newInvoice);
				}
				invoiceList.add(invoice);
			}
		}
		return invoiceList;
	}
}
