package com.revolut.banking.service;

public class AccountServiceFactory {

	private static AccountService accountService;
	
	private AccountServiceFactory() {
	}
	
	public static AccountService getAccountService() {
		if(accountService==null) {
			accountService = new AccountServiceImpl(); 
		}
		return accountService;
	}
	
}
