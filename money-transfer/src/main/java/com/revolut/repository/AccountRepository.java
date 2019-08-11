package com.revolut.repository;

import com.revolut.entities.Account;

public interface AccountRepository {

	Account addAccount(Account account);

	Account findAccount(long id);

}
