package com.revolut.banking.exceptions;

public class InvalidAccountException extends Exception {

	private static final long serialVersionUID = 1032419556358568728L;

	public InvalidAccountException(String message) {
		super(message);
	}
}
