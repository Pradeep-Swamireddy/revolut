package com.revolut.repository.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jboss.logging.Logger;
import org.junit.Test;

import com.revolut.entities.Bank;
import com.revolut.repository.BankRepository;
import com.revolut.repository.BankRepositoryImpl;

public class BankRepositoryTest {
	public static final Logger LOG = Logger.getLogger(BankRepositoryTest.class);

	@Test
	public void testAddBank() {
		BankRepository bankRepository = new BankRepositoryImpl();
		Bank bank = new Bank();
		bank.setCode("B30");
		bank.setBankName("Lloyds");
		bank.setAddress("East Ham, London");
		boolean result = bankRepository.addBank(bank);
		assertEquals(true, result);
		if (result)
			LOG.info("Bank added successfully");
	}

}
