package com.revolut.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.entities.Bank;

public class BankRepositoryImpl implements BankRepository, HibernateRepository {

	public static final Logger LOG = Logger.getLogger(BankRepositoryImpl.class);

	private static SessionFactory sessionFactory;

	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateRepository.createSessionFactory();
		}
		return sessionFactory;
	}

	@Override
	public boolean addBank(Bank bank) {
		boolean bankAdded = false;
		Transaction transaction = null;
		try (Session session = getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(bank);
			transaction.commit();
			bankAdded = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return bankAdded;
	}

	@Override
	public Bank findBank(String code) {
		Bank foundBank = null;
		try (Session session = getSessionFactory().openSession()) {
			Bank bank = new Bank();
			bank.setCode(code);
			foundBank = session.get(Bank.class, code);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return foundBank;
	}

}
