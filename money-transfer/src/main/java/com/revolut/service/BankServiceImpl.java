package com.revolut.service;

import com.revolut.entities.Bank;
import com.revolut.repository.BankRepository;
import com.revolut.repository.BankRepositoryImpl;

public class BankServiceImpl implements BankService {

	@Override
	public boolean addBank(Bank bank) {
		BankRepository bankRepository = new BankRepositoryImpl();
		return bankRepository.addBank(bank);
	}

}
