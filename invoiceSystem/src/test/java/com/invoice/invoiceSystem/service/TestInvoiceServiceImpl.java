package com.invoice.invoiceSystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invoice.invoiceSystem.model.Invoice;
import com.invoice.invoiceSystem.repository.InvoiceRepository;

@ExtendWith(MockitoExtension.class)
public class TestInvoiceServiceImpl {
	@InjectMocks
	InvoiceServiceImpl invoiceServiceMock;
	@Mock
	InvoiceRepository invoiceRepositoryMock;
	
	@Test
	void testCreateInvoice() {
		Invoice invoice = new Invoice();
		invoice.setId(1L);
		invoice.setAmount(1122.0);
		invoice.setDueDate(LocalDate.now());
		Mockito.when(invoiceRepositoryMock.save(Mockito.any())).thenReturn(invoice);
		invoice = invoiceServiceMock.createInvoice(invoice.getAmount(), invoice.getDueDate());
		Assertions.assertNotNull(invoice);
	}
	@Test
	void testgetAllInvoices() {
		List<Invoice> invoices = new ArrayList();
		Invoice invoice = new Invoice();
		invoice.setId(1L);
		invoice.setAmount(1122.0);
		invoices.add(invoice);
		Mockito.when(invoiceRepositoryMock.findAll()).thenReturn(invoices);
		invoices = invoiceServiceMock.getAllInvoices();
		Assertions.assertNotNull(invoices);
	}
	@Test
	void testgetInvoiceById() {
		Invoice invoice = new Invoice();
		invoice.setId(1L);
		invoice.setAmount(1122.0);
		Mockito.when(invoiceRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(invoice));
		Optional<Invoice> invoiceFromDB = invoiceServiceMock.getInvoiceById(1L);
		Assertions.assertNotNull(invoiceFromDB);
	}
	@Test
	void testmakePayment() {
		Invoice invoice = new Invoice();
		invoice.setId(1L);
		invoice.setAmount(1122.0);
		Mockito.when(invoiceRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(invoice));
		invoice = invoiceServiceMock.makePayment(1L, 1122.0);
		Assertions.assertNull(invoice);
	}
	@Test
	void testprocessOverdueInvoices() {
		List<Invoice> invoices = new ArrayList();
		Invoice invoice = new Invoice();
		invoice.setId(1L);
		invoice.setAmount(1122.0);
		invoice.setStatus("pending");
		invoice.setDueDate(LocalDate.of(2023, 8, 8));
		invoice.setPaidAmount(1100.0);
		invoices.add(invoice);
		Invoice newInvoice = new Invoice();
		newInvoice.setId(1L);
		newInvoice.setAmount(1122.0);
		newInvoice.setStatus("pending");
		newInvoice.setDueDate(LocalDate.of(2023, 8, 8));
		newInvoice.setPaidAmount(0.0);
		invoices.add(newInvoice);
		Mockito.when(invoiceRepositoryMock.findAll()).thenReturn(invoices);
		invoiceServiceMock.processOverdueInvoices(12.0,  4);
	}
}
