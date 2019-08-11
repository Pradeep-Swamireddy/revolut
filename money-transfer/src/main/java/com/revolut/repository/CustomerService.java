package com.revolut.repository;

import com.revolut.entities.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer, String bankCode);

}
