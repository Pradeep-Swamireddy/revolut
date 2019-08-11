package com.revolut.service;

import com.revolut.entities.Bank;

public interface BankService {

	Bank addBank(Bank bank);
	
	Bank findBank(String bankCode); 
	
}
