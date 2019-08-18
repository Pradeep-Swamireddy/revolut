package com.revolut.banking.service;

public class TransferServiceFactory {

	private static TransferService transferService;

	public static TransferService getCustomerService() {
		if (transferService == null) {
			transferService = new TransferServiceImpl();
		}
		return transferService;
	}

	private TransferServiceFactory() {
	}
}
