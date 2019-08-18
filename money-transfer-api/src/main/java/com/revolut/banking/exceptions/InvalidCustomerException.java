package com.revolut.banking.exceptions;

public class InvalidCustomerException extends Exception {

	private static final long serialVersionUID = -4723077288713330971L;

	public InvalidCustomerException(String message) {
		super(message);
	}
}
