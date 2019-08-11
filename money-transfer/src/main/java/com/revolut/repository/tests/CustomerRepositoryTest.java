package com.revolut.repository.tests;

import static org.junit.Assert.assertNotEquals;

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
		bank = new Bank();
		bank.setCode("B20");
		bank.setBankName("Barclays");
		bank.setAddress("Northampton");
	}

	@Test
	public void testAddCustomer() {
		CustomerRepository customerRepository = new CustomerRepositoryImpl();
		Customer customer = new Customer("Pradeep", "Swamireddy", "520 Alpha House, Northampton, NN1 2HQ");
		customer.setBank(bank);
		Customer newCustomer = customerRepository.addCustomer(customer);
		assertNotEquals(null, newCustomer);
		if (newCustomer != null)
			LOG.info("Customer added successfully with Id: " + newCustomer.getId());
		else
			LOG.info("Failed to create Customer: " + customer.getFirstName());
	}

}
