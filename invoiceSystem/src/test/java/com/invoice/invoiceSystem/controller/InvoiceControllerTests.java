package com.invoice.invoiceSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.invoice.invoiceSystem.model.Invoice;
import com.invoice.invoiceSystem.model.InvoiceRequest;
import com.invoice.invoiceSystem.model.PaymentRequest;
import com.invoice.invoiceSystem.model.ProcessOverdueRequest;
import com.invoice.invoiceSystem.service.InvoiceService;

@ExtendWith(MockitoExtension.class)
public class InvoiceControllerTests {
	@InjectMocks
	InvoiceController invoiceControllerMock;
	@Mock
	InvoiceService invoiceServiceMock;
	@Test
	void testCreateInvoice() {
		InvoiceRequest invoiceRequest = new InvoiceRequest();
		invoiceRequest.setAmount(1122.00);
		invoiceRequest.setDueDate(LocalDate.now());
		Invoice invoice = new Invoice();
		invoice.setId(1L);
		invoice.setAmount(invoiceRequest.getAmount());
		Mockito.when(invoiceServiceMock.createInvoice(Mockito.anyDouble(), Mockito.any())).thenReturn(invoice);
		ResponseEntity<?> createInvoice = invoiceControllerMock.createInvoice(invoiceRequest);
		Assertions.assertEquals(createInvoice.getStatusCodeValue(), 201);
	}
	@Test
	void testGetAllInvoice() {
		List<Invoice> invoices = new ArrayList<Invoice>();
		Invoice invoice = new Invoice();
		invoice.setAmount(1122.0);
		invoice.setDueDate(LocalDate.now());
		invoices.add(invoice);
		Mockito.when(invoiceServiceMock.getAllInvoices()).thenReturn(invoices);
		ResponseEntity<List<Invoice>> invoiceResp = invoiceControllerMock.getAllInvoices();
		Assertions.assertNotNull(invoiceResp);
		Assertions.assertEquals(invoiceResp.getStatusCodeValue(), 200);
	}
	@Test
	void testGetAllInvoiceNullResponse() {
		Mockito.when(invoiceServiceMock.getAllInvoices()).thenReturn(null);
		ResponseEntity<List<Invoice>> invoiceResp = invoiceControllerMock.getAllInvoices();
		Assertions.assertNotNull(invoiceResp);
		Assertions.assertEquals(invoiceResp.getStatusCodeValue(), 200);
	}
	@Test
	void testMakePayment() {
		PaymentRequest paymentReq = new PaymentRequest();
		paymentReq.setAmount(1122.0);
		ResponseEntity<Invoice> makePayment = invoiceControllerMock.makePayment(1122L, paymentReq);
		Assertions.assertNotNull(makePayment);
		Assertions.assertEquals(200, makePayment.getStatusCodeValue());
	}
	@Test
	void testProcessOverdueInvoices() {
		ProcessOverdueRequest req = new ProcessOverdueRequest();
		req.setLateFee(11.0);
		req.setOverdueDays(10);
		ResponseEntity<?> processOverdueInvoices = invoiceControllerMock.processOverdueInvoices(req);
		Assertions.assertNotNull(processOverdueInvoices);
		Assertions.assertEquals(200, processOverdueInvoices.getStatusCodeValue());
	}

}
