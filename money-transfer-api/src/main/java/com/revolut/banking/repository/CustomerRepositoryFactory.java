package com.revolut.banking.repository;

public class CustomerRepositoryFactory {

	private static CustomerRepository customerRepository;

	private CustomerRepositoryFactory() {
	}

	public static CustomerRepository getCustomerRepository() {
		if (customerRepository == null) {
			customerRepository = new CustomerRepositoryImpl();
		}
		return customerRepository;
	}

}
