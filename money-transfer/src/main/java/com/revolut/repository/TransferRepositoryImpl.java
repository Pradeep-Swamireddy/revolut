package com.revolut.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.entities.Account;
import com.revolut.entities.Transfer;
import com.revolut.service.AccountService;
import com.revolut.service.AccountServiceImpl;

public class TransferRepositoryImpl implements TransferRepository, HibernateRepository {

	public static final Logger LOG = Logger.getLogger(TransferRepositoryImpl.class);

	private AccountService accountService = new AccountServiceImpl();

	private static SessionFactory sessionFactory;

	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateRepository.createSessionFactory();
		}
		return sessionFactory;
	}

	@Override
	public Transfer makeTransfer(Transfer transfer) {
		Transaction transaction = null;
		try (Session session = getSessionFactory().openSession()) {
			synchronized (session) {
				transaction = session.beginTransaction();
				Account sender = accountService.findAccount(transfer.getSender().getId());
				Account receiver = accountService.findAccount(transfer.getReceiver().getId());
				if (sender != null && receiver != null) {
					sender.setBalance(sender.getBalance() - transfer.getAmount());
					receiver.setBalance(receiver.getBalance() - transfer.getAmount());
				}
				transfer.setStatus(true);
				transaction.commit();
			}
			return transfer;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOG.error(e.getMessage());
		}
		return null;
	}

}
