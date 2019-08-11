package com.revolut.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.revolut.exceptions.InvalidBalanceException;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long balance;

	@OneToOne(mappedBy = "account")
	private Customer customer;

	public Account(long balance) {
		this.balance = balance;
	}
	
	public Account() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) throws InvalidBalanceException {
		if (balance >= 0)
			this.balance = balance;
		else
			throw new InvalidBalanceException("Balance cannot be less than 0");
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
