package com.invoice.invoiceSystem.model;

import java.time.LocalDate;


public class InvoiceRequest {
	 private Double amount;
	 public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	private LocalDate dueDate;
}
