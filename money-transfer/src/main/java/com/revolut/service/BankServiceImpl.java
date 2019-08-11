package com.revolut.service;

import com.revolut.entities.Bank;
import com.revolut.repository.BankRepository;
import com.revolut.repository.BankRepositoryImpl;

public class BankServiceImpl implements BankService {

	private BankRepository bankRepository = new BankRepositoryImpl();

	@Override
	public boolean addBank(Bank bank) {
		return bankRepository.addBank(bank);
	}

	@Override
	public Bank findBank(String bankCode) {
		return bankRepository.findBank(bankCode);
	}

}
