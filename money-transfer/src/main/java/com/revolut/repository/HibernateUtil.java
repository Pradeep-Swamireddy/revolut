package com.revolut.repository;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

import com.revolut.entities.Account;
import com.revolut.entities.Bank;
import com.revolut.entities.Customer;
import com.revolut.entities.Transfer;

public class HibernateUtil {
	public static final Logger LOG = Logger.getLogger(HibernateUtil.class);

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = createSessionFactory();
		}
		return sessionFactory;
	}

	public static SessionFactory createSessionFactory() {
		SessionFactory sessionFactory = null;
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
			configuration.addAnnotatedClass(Bank.class).addAnnotatedClass(Customer.class)
					.addAnnotatedClass(Account.class).addAnnotatedClass(Transfer.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return sessionFactory;
	}
}
