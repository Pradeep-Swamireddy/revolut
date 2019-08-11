package com.revolut.repository;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

import com.revolut.entities.Account;
import com.revolut.entities.Bank;
import com.revolut.entities.Customer;

public class BankRepositoryImpl implements BankRepository {

	public static final Logger LOG = Logger.getLogger(BankRepositoryImpl.class);

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "org.h2.Driver");
				settings.put(Environment.URL, "jdbc:h2:mem:testdb");
				settings.put(Environment.USER, "sa");
				settings.put(Environment.PASS, "password");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "create-drop");
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(Bank.class).addAnnotatedClass(Customer.class).addAnnotatedClass(Account.class);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
		return sessionFactory;
	}

	@Override
	public boolean addBank(Bank bank) {
		boolean bankAdded=false;
		Transaction transaction = null;
		try (Session session = getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(bank);
			transaction.commit();
			bankAdded=true;
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
		Bank foundBank=null;
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
