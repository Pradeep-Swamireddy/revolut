package com.revolut.repository;

import com.revolut.entities.Bank;

public interface BankRepository {

	Bank addBank(Bank bank);
	Bank findBank(String code);
}
