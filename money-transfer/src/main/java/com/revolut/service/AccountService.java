package com.revolut.service;

import com.revolut.entities.Account;

public interface AccountService {

	Account addAccount(Account account, String bankCode, long customerId);

}
