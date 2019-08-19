package com.revolut.banking.entities;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.revolut.banking.exceptions.InvalidBalanceException;

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "accountNo")
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty
	private long accountNo;
	@NotNull
	@Min(value = 0)
	@JsonProperty
	private BigDecimal balance;
	@Version
	@JsonIgnore
	private long version;
	@OneToOne(mappedBy = "account")
	private Customer customer;

	@JsonIgnore
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Transfer> senders;

	@JsonIgnore
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Transfer> receivers;

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) throws InvalidBalanceException {
		if (balance.compareTo(BigDecimal.valueOf(0.00)) < 0) {
			throw new InvalidBalanceException("Insufficient balance for transfer: " + this.balance);
		}
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Transfer> getSenders() {
		return senders;
	}

	public void setSenders(Set<Transfer> senders) {
		this.senders = senders;
	}

	public Set<Transfer> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<Transfer> receivers) {
		this.receivers = receivers;
	}

	@Override
	public String toString() {
		return String.format("Account No: %s, Balance: %s, Customer: %s", accountNo, balance, customer);
	}

}
