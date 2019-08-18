package com.revolut.banking.exceptions;

public class TransferException extends Exception {

	private static final long serialVersionUID = 1612669080869242906L;

	public TransferException(String message) {
		super(message);
	}
}
