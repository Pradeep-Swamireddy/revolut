package com.revolut.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.entities.Customer;

public class CustomerRepositoryImpl implements CustomerRepository, HibernateRepository {

	public static final Logger LOG = Logger.getLogger(CustomerRepositoryImpl.class);

	private static SessionFactory sessionFactory;

	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateRepository.createSessionFactory();
		}
		return sessionFactory;
	}

	@Override
	public Customer addCustomer(Customer customer) {
		Transaction transaction = null;
		try (Session session = getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(customer.getBank());
			session.save(customer);
			transaction.commit();
			return customer;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return null;
	}

}
