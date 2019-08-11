package com.revolut.service;

import org.jboss.logging.Logger;

import com.revolut.entities.Account;
import com.revolut.entities.Customer;
import com.revolut.repository.AccountRepository;
import com.revolut.repository.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountService {

	public static final Logger LOG = Logger.getLogger(AccountServiceImpl.class);

	private CustomerService custService = new CustomerServiceImpl();

	private AccountRepository accountRepo = new AccountRepositoryImpl();

	@Override
	public Account addAccount(Account account, String bankCode, long customerId) {
		Customer customer = custService.findCustomer(customerId, bankCode);
		if (customer != null) {
			account.setCustomer(customer);
			return accountRepo.addAccount(account);
		} else {
			LOG.error("Invalid Customer Id: " + customerId + " Or Bank code: " + bankCode);
		}
		return null;
	}

	@Override
	public Account findAccount(long id) {
		return accountRepo.findAccount(id);
	}

}
