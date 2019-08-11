package com.revolut.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import com.revolut.entities.Bank;
import com.revolut.entities.Customer;

public class CustomerRepositoryImpl implements CustomerRepository {

	public static final Logger LOG = Logger.getLogger(CustomerRepositoryImpl.class);

	@Override
	public Customer addCustomer(Customer customer) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			// session.save(customer.getBank());
			session.save(customer);
			transaction.commit();
			return customer;
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
	public Customer findCustomer(long customerId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Customer.class, customerId);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Customer findCustomer(long customerId, String bankCode) {
		Customer cust = new Customer();
		Bank bank = new Bank();
		bank.setCode(bankCode);
		cust.setBank(bank);
		cust.setId(customerId);
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
			Root<Customer> root = query.from(Customer.class);
			Predicate equalCustId = builder.equal(root.get("id"), customerId);
			Predicate equalBankCode = builder.equal(root.get("bank"), bank);
			query.select(root).where(builder.and(equalCustId, equalBankCode));
			Query<Customer> q = session.createQuery(query);
			List<Customer> list = q.getResultList();
			if (list.size() == 1) {
				return list.get(0);
			} else {
				LOG.error("Found more than 1 result for Customer Id: " + customerId + " ,Bank code: " + bankCode);
				list.forEach(c -> LOG.error(c.getId() + c.getFirstName()));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

}
