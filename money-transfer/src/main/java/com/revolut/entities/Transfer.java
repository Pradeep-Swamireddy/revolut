package com.revolut.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transfer {
	@Id
	private long transactionId;
	@ManyToOne(targetEntity = Account.class, optional = false)
	private Account sender;
	@ManyToOne(targetEntity = Account.class, optional = false)
	private Account receiver;
	private long amount;
	private boolean status;

	public Transfer(Account sender, Account receiver, long amount) {
		this.amount = amount;
		this.sender = sender;
		this.receiver = receiver;
	}

	public Transfer() {
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}

	public Account getReceiver() {
		return receiver;
	}

	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
