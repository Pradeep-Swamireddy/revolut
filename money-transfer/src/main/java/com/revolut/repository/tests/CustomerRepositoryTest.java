package com.revolut.repository.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Test;

import com.revolut.entities.Bank;
import com.revolut.entities.Customer;
import com.revolut.repository.CustomerRepository;
import com.revolut.repository.CustomerRepositoryImpl;

public class CustomerRepositoryTest {
	public static final Logger LOG = Logger.getLogger(CustomerRepositoryTest.class);

	private Bank bank;
	@Before
	public void setup() {
		Bank bank = new Bank();
		bank.setCode("B30");
		bank.setBankName("Lloyds");
		bank.setAddress("East Ham, London");
	}
	
	@Test
	public void testAddCustomer() {
		CustomerRepository customerRepository = new CustomerRepositoryImpl();
		Customer customer = new Customer();
		customer.setBank(bank);
		customer.setAddress("45 Sheppard St, Canning Town");
		customer.setFirstName("Pradeep");
		customer.setLastName("Swamireddy");
		boolean result = customerRepository.addCustomer(customer);
		assertEquals(true, result);
		if (result)
			LOG.info("Bank added successfully");
	}

}
