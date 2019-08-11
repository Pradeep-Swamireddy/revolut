package com.revolut.service;

import org.jboss.logging.Logger;

import com.revolut.entities.Account;
import com.revolut.entities.Transfer;
import com.revolut.repository.TransferRepository;
import com.revolut.repository.TransferRepositoryImpl;

public class TransferServiceImpl implements TransferService {

	public static final Logger LOG = Logger.getLogger(TransferServiceImpl.class);

	private AccountService accountService = new AccountServiceImpl();
	
	private TransferRepository transferRepo = new TransferRepositoryImpl();

	@Override
	public Transfer moneyTransfer(Transfer transfer) {
		Account sender = accountService.findAccount(transfer.getSender().getId());
		Account receiver = accountService.findAccount(transfer.getReceiver().getId());
		transfer.setSender(sender);
		transfer.setReceiver(receiver);
		if (transfer.getAmount() <= 0) {
			LOG.error(String.format("Invalid amount: %s in transfer", transfer.getAmount()));
		}
		else {
			return transferRepo.makeTransfer(transfer);
		}
		return null;
	}

}
