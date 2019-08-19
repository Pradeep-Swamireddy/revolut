package com.revolut.banking.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.entities.Customer;

public class CustomerRepositoryImpl implements CustomerRepository {

	public static final Logger LOG = LoggerFactory.getLogger(CustomerRepositoryImpl.class);

	@Override
	public Customer addCustomer(Customer customer) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(customer);
			transaction.commit();
			LOG.info("Customer created successfully- {}", customer);
			return customer;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean deleteCustomer(String customerId) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Customer customer = session.load(Customer.class, customerId);
			session.delete(customer);
			transaction.commit();
			LOG.info("Customer deleted successfully- {}", customer);
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return false;
	}

	@Override
	public Customer findCustomer(String customerId) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Customer customer = session.get(Customer.class, customerId);
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
