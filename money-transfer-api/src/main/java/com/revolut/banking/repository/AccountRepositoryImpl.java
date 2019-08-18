package com.revolut.banking.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.entities.Account;
import com.revolut.banking.entities.Customer;
import com.revolut.banking.exceptions.InvalidAccountException;
import com.revolut.banking.exceptions.InvalidCustomerException;

public class AccountRepositoryImpl implements AccountRepository {

	public static final Logger LOG = LoggerFactory.getLogger(AccountRepositoryImpl.class);

	public Account addAccount(Account account, String customerId) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
			Customer customer = session.get(Customer.class, customerId);
			if (customer != null) {
				account.setCustomer(customer);
				customer.setAccount(account);
				transaction = session.beginTransaction();
				session.save(account);
				transaction.commit();
				LOG.info("Account created successfully- {}",account);
				return account;
			} else {
				throw new InvalidCustomerException("Customer Id: " + customerId + " is invalid");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Account findAccount(long accountNo) {
		LOG.info("Inside");
		try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
			Account account = session.get(Account.class, accountNo);
			if (account != null) {
				return account;
			} else {
				throw new InvalidAccountException("Account No: " + accountNo + " is invalid");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

}
