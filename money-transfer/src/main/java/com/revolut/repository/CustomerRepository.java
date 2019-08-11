package com.revolut.repository;

import com.revolut.entities.Customer;

public interface CustomerRepository {

	Customer addCustomer(Customer customer);

	Customer findCustomer(long customerId);

	Customer findCustomer(long customerId, String bankCode);

}
