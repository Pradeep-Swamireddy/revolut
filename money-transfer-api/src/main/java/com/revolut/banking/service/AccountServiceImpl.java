package com.revolut.banking.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.entities.Account;
import com.revolut.banking.repository.AccountRepository;
import com.revolut.banking.repository.AccountRepositoryFactory;
import com.revolut.banking.validation.ValidationFactory;

public class AccountServiceImpl implements AccountService {

	public static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
	private AccountRepository accountRepo = AccountRepositoryFactory.getAccountRepository();

	@Override
	public Account addAccount(Account account, String customerId) {
		Validator validator = ValidationFactory.getValidator();
		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
		if (constraintViolations.isEmpty()) {
			return accountRepo.addAccount(account, customerId);
		} else {
			constraintViolations.forEach(a -> LOG.info("{},{}", a.getConstraintDescriptor(), a.getMessage()));
		}
		return null;
	}

	@Override
	public Account findAccount(long accountNo) {
		return accountRepo.findAccount(accountNo);
	}

}
