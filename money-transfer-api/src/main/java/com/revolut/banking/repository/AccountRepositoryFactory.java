package com.revolut.banking.repository;

public class AccountRepositoryFactory {
	
	private static AccountRepository accountRepo;

	public static AccountRepository getAccountRepository() {
		if (accountRepo == null) {
			accountRepo = new AccountRepositoryImpl();
		}
		return accountRepo;
	}

}
