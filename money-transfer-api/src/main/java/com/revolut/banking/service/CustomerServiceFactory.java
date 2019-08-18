package com.revolut.banking.service;

public class CustomerServiceFactory {

	private static CustomerService CustomerService;

	private CustomerServiceFactory() {
	}

	public static CustomerService getCustomerService() {
		if (CustomerService == null) {
			CustomerService = new CustomerServiceImpl();
		}
		return CustomerService;
	}

}
