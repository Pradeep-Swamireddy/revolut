package com.revolut.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
	@JsonProperty("AccountId")
	private long accountId;

	@JsonProperty("Name")
	private String accountHolderName;

	@JsonProperty("InitialBalance")
	private long balance;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return String.format("Id : %s , Name : %s , Balance : %s", this.accountId, this.accountHolderName,
				this.balance);
	}
	@JsonCreator
	public Account(@JsonProperty("AccountId") long accountId, @JsonProperty("Name") String accountHolderName, @JsonProperty("InitialBalance") long balance) {
		this.accountId = accountId;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
	}
}
