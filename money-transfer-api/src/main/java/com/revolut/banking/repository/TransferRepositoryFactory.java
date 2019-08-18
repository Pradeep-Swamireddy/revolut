package com.revolut.banking.repository;

public class TransferRepositoryFactory {

	private static TransferRepository transferRepository;

	public static TransferRepository getAccountRepository() {
		if (transferRepository == null) {
			transferRepository = new TransferRepositoryImpl();
		}
		return transferRepository;
	}

	private TransferRepositoryFactory() {
	}
}
