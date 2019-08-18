package com.revolut.banking.exceptions;

public class InvalidBalanceException extends Exception {
	private static final long serialVersionUID = -8122932696804792756L;

	public InvalidBalanceException(String message) {
		super(message);
	}
}
