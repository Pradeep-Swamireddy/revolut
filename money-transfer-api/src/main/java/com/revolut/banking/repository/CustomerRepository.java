package com.revolut.banking.repository;

import com.revolut.banking.entities.Customer;

public interface CustomerRepository {

	Customer addCustomer(Customer customer);

	boolean deleteCustomer(String customerId);

}
