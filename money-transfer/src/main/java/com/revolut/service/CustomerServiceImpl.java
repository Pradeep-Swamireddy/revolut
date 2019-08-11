package com.revolut.service;

import org.jboss.logging.Logger;

import com.revolut.entities.Bank;
import com.revolut.entities.Customer;
import com.revolut.repository.CustomerRepository;
import com.revolut.repository.CustomerRepositoryImpl;

public class CustomerServiceImpl implements CustomerService {

	public static final Logger LOG = Logger.getLogger(CustomerServiceImpl.class);

	private BankService bankService = new BankServiceImpl();

	private CustomerRepository custRepo = new CustomerRepositoryImpl();

	@Override
	public Customer addCustomer(Customer customer, String bankCode) {
		LOG.info("Bank Code: "+bankCode);
		Bank bank = bankService.findBank(bankCode);
		if (bank != null) {
			customer.setBank(bank);
			return custRepo.addCustomer(customer);
		} else
			LOG.error("Bank not found with code: " + bankCode);
		return null;
	}

	@Override
	public Customer findCustomer(long customerId) {
		return custRepo.findCustomer(customerId);
	}

	@Override
	public Customer findCustomer(long customerId, String bankCode) {
		return custRepo.findCustomer(customerId, bankCode);
	}

}
