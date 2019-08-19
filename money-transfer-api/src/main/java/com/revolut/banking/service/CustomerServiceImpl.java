package com.revolut.banking.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.entities.Customer;
import com.revolut.banking.repository.CustomerRepository;
import com.revolut.banking.repository.CustomerRepositoryFactory;
import com.revolut.banking.validation.ValidationFactory;

public class CustomerServiceImpl implements CustomerService {

	public static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);
	private CustomerRepository customerRepo = CustomerRepositoryFactory.getCustomerRepository();

	@Override
	public Customer addCustomer(Customer customer) {
		Validator validator = ValidationFactory.getValidator();
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
		if (constraintViolations.isEmpty()) {
			return customerRepo.addCustomer(customer);
		} else {
			constraintViolations.forEach(c -> LOG.info("{},{}", c.getConstraintDescriptor(), c.getMessage()));
		}
		return null;
	}

	@Override
	public boolean deleteCustomer(String customerId) {
		return customerRepo.deleteCustomer(customerId);
	}

	@Override
	public Customer findCustomer(String customerId) {
		return customerRepo.findCustomer(customerId);
	}

}
