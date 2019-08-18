package com.revolut.banking.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.entities.Transfer;
import com.revolut.banking.exceptions.TransferException;
import com.revolut.banking.repository.TransferRepository;
import com.revolut.banking.repository.TransferRepositoryFactory;
import com.revolut.banking.validation.ValidationFactory;

public class TransferServiceImpl implements TransferService {

	public static final Logger LOG = LoggerFactory.getLogger(TransferServiceImpl.class);
	private TransferRepository accountRepo = TransferRepositoryFactory.getAccountRepository();
	
	@Override
	public Transfer transferMoney(Transfer transaction) throws TransferException {
		Validator validator = ValidationFactory.getValidator();
		Set<ConstraintViolation<Transfer>> constraintViolations = validator.validate(transaction);
		if (constraintViolations.isEmpty()) {
			return accountRepo.transferMoney(transaction);
		} else {
			constraintViolations.forEach(a -> LOG.info("{},{}", a.getConstraintDescriptor(), a.getMessage()));
		}
		return null;
	}
	
}
