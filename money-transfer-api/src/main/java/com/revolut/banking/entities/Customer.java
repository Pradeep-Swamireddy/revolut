package com.revolut.banking.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "customerId")
@Entity
public class Customer {
	@Id
	@NotNull
	private String customerId;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;
	@NotNull
	@Size(max = 50)
	private String fullName;
	private String address;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return String.format("Customer Id: %s, Name: %s , Address: %s , Account Id: %s", customerId, fullName,address,
				account != null ? account.getAccountNo() : null);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
