package com.revolut.banking.service;

import com.revolut.banking.entities.Account;

public interface AccountService {

	Account addAccount(Account account, String customerId);

	Account findAccount(long accountId);
}
