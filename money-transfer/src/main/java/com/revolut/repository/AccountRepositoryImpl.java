package com.revolut.repository;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.entities.Account;

public class AccountRepositoryImpl implements AccountRepository {

	public static final Logger LOG = Logger.getLogger(AccountRepositoryImpl.class);

	@Override
	public Account addAccount(Account account) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			//session.save(account.getCustomer().getBank());
			//session.save(account.getCustomer());
			session.save(account);
			transaction.commit();
			return account;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account findAccount(long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Account.class, id);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

}
