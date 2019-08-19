package com.revolut.banking.service;

import com.revolut.banking.entities.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer);

	boolean deleteCustomer(String customerId);

	Customer findCustomer(String customerId);

}
