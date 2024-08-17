package com.invoice.invoiceSystem.model;

public class InvoiceResponse {
    private Long id;

    public InvoiceResponse(Long id) {
        this.id = id;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
