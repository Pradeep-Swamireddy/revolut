package com.revolut.banking.repository;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

import com.revolut.banking.entities.Account;
import com.revolut.banking.entities.Customer;
import com.revolut.banking.entities.Transfer;

public class HibernateSessionFactory {
	public static final Logger LOG = Logger.getLogger(HibernateSessionFactory.class);

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = createSessionFactory();
		}
		return sessionFactory;
	}

	private HibernateSessionFactory() {
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
			/*
			 * settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
			 * settings.put(Environment.URL,
			 * "jdbc:mysql://localhost:3306/pravartakdb?verifyServerCertificate=false&useSSL=false&allowPublicKeyRetrieval=true&requireSSL=false"
			 * ); settings.put(Environment.USER, "SYSTEM"); settings.put(Environment.PASS,
			 * "SYSTEM");
			 */
			settings.put(Environment.SHOW_SQL, "false");
			settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
			settings.put(Environment.HBM2DDL_AUTO, "create-drop");
			settings.put(Environment.AUTOCOMMIT, "false");
			configuration.setProperties(settings);
			configuration.addAnnotatedClass(Customer.class).addAnnotatedClass(Account.class)
					.addAnnotatedClass(Transfer.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return sessionFactory;
	}
}
