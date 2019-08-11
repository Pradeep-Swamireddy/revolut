package com.revolut.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.entities.Account;
import com.revolut.entities.Transfer;
import com.revolut.exceptions.InvalidBalanceException;
import com.revolut.service.AccountService;
import com.revolut.service.AccountServiceImpl;

public class TransferRepositoryImpl implements TransferRepository {

	public static final Logger LOG = Logger.getLogger(TransferRepositoryImpl.class);

	private AccountService accountService = new AccountServiceImpl();

	@Override
	public Transfer makeTransfer(Transfer transfer) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			synchronized (session) {
				transaction = session.beginTransaction();
				Account sender = accountService.findAccount(transfer.getSender().getId());
				Account receiver = accountService.findAccount(transfer.getReceiver().getId());
				if (sender != null && receiver != null) {
					LOG.info(String.format("Balance before transfer - Sender: %s , Receiver: %s", sender.getBalance(),
							receiver.getBalance()));
					try {
						sender.setBalance(sender.getBalance() - transfer.getAmount());
					} catch (InvalidBalanceException ex) {
						LOG.error("Insufficient balance to make transaction of Amount: " + transfer.getAmount());
						throw ex;
					}
					receiver.setBalance(receiver.getBalance() + transfer.getAmount());
					transfer.setStatus(true);
					transaction.commit();
					LOG.info(String.format("Balance After transfer - Sender: %s , Receiver: %s", sender.getBalance(),
							receiver.getBalance()));
				} else {
					LOG.error(String.format("Transaction failed between Sender Id: %s and Receiver Id: %s",
							transfer.getSender().getId(), transfer.getReceiver().getId()));
				}
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
