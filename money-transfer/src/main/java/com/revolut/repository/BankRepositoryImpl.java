package com.revolut.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.entities.Bank;

public class BankRepositoryImpl implements BankRepository {

	public static final Logger LOG = Logger.getLogger(BankRepositoryImpl.class);

	@Override
	public Bank addBank(Bank bank) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(bank);
			transaction.commit();
			return bank;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Bank findBank(String code) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Bank.class, code);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
