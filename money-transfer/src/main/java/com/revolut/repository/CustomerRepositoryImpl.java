package com.revolut.repository;

import com.revolut.entities.Bank;
import com.revolut.entities.Customer;

public class CustomerRepositoryImpl implements CustomerRepository{

	@Override
	public boolean addCustomer(Customer customer) {
		Bank custBank = new BankRepositoryImpl().findBank(customer.getBank().getCode());
		if(custBank!=null) {
			customer.setBank(custBank);
			
		}
		return false;
	}

}
