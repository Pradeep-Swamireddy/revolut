package com.revolut.repository;

import com.revolut.entities.Bank;

public interface BankRepository {

	boolean addBank(Bank bank);
	Bank findBank(String code);
}
