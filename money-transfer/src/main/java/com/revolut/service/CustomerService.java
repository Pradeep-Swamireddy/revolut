package com.revolut.service;

import com.revolut.entities.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer, String bankCode);

	Customer findCustomer(long customerId);

	Customer findCustomer(long customerId, String bankCode);

}
