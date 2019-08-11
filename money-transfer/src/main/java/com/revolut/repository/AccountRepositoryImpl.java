package com.revolut.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.entities.Account;

public class AccountRepositoryImpl implements AccountRepository,HibernateRepository{

	public static final Logger LOG = Logger.getLogger(AccountRepositoryImpl.class);

	private static SessionFactory sessionFactory;

	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateRepository.createSessionFactory();
		}
		return sessionFactory;
	}
	
	@Override
	public Account addAccount(Account account) {
		Transaction transaction = null;
		try (Session session = getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(account.getCustomer().getBank());
			session.save(account.getCustomer());
			session.save(account);
			transaction.commit();
			return account;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return null;
	}

}
