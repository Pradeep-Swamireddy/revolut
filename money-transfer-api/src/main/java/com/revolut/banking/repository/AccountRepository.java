package com.revolut.banking.repository;

import com.revolut.banking.entities.Account;

public interface AccountRepository {

	Account addAccount(Account account, String customerId);

	Account findAccount(long accountNo);

}
