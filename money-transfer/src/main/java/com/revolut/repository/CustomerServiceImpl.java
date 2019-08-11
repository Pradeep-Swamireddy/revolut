package com.revolut.repository;

import org.jboss.logging.Logger;

import com.revolut.entities.Bank;
import com.revolut.entities.Customer;
import com.revolut.service.BankService;
import com.revolut.service.BankServiceImpl;

public class CustomerServiceImpl implements CustomerService {

	public static final Logger LOG = Logger.getLogger(CustomerServiceImpl.class);

	private BankService bankService = new BankServiceImpl();

	private CustomerRepository custRepo = new CustomerRepositoryImpl();

	@Override
	public Customer addCustomer(Customer customer, String bankCode) {
		Bank bank = bankService.findBank(bankCode);
		if (bank != null) {
			customer.setBank(bank);
			return custRepo.addCustomer(customer);
		} else
			LOG.error("Bank not found with code: " + bankCode);
		return null;
	}

}
