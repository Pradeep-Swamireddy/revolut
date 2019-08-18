package com.revolut.banking.repository;

import java.math.RoundingMode;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import com.revolut.banking.entities.Account;
import com.revolut.banking.entities.Transfer;
import com.revolut.banking.exceptions.TransferException;
import com.revolut.banking.service.AccountService;
import com.revolut.banking.service.AccountServiceFactory;

public class TransferRepositoryImpl implements TransferRepository {

	public static final Logger LOG = Logger.getLogger(TransferRepositoryImpl.class);
	private AccountService accountService = AccountServiceFactory.getAccountService();

	@Override
	public Transfer transferMoney(Transfer transfer) throws TransferException {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Account sender = accountService.findAccount(transfer.getSender().getAccountNo());
			Account receiver = accountService.findAccount(transfer.getReceiver().getAccountNo());
			if (sender != null && receiver != null) {
				LOG.info(String.format("Balance before transfer - Sender: %s , Receiver: %s", sender.getBalance(),
						receiver.getBalance()));
				sender.setBalance(
						sender.getBalance().subtract(transfer.getTransferAmount()).setScale(2, RoundingMode.HALF_DOWN));
				receiver.setBalance(
						receiver.getBalance().add(transfer.getTransferAmount()).setScale(2, RoundingMode.HALF_UP));
				transfer.setSender(sender);
				transfer.setReceiver(receiver);
				session.update(sender);
				session.update(receiver);
				session.save(transfer);
				transaction.commit();
				LOG.info(String.format("Balance After transfer - Sender: %s , Receiver: %s", sender.getBalance(),
						receiver.getBalance()));
			} else {
				LOG.error(String.format("Transaction failed between Sender Id: %s and Receiver Id: %s",
						transfer.getSender().getAccountNo(), transfer.getReceiver().getAccountNo()));
				throw new TransferException(String.format(
						"Sender/Receiver not found. Transaction failed between Sender Id: %s and Receiver Id: %s",
						transfer.getSender().getAccountNo(), transfer.getReceiver().getAccountNo()));
			}
			return transfer;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new TransferException(e.getMessage());
		}
	}

}
