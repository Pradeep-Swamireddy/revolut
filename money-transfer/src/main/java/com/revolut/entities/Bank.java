package com.revolut.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Bank {
	@Id
	@JsonProperty("BankCode")
	private String code;

	@JsonProperty("Name")
	private String bankName;

	@JsonProperty("Address")
	private String address;

	@JsonProperty
	@OneToMany
	private Set<Customer> customers;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Customer> getAccounts() {
		return customers;
	}

	public void setAccounts(Set<Customer> accounts) {
		this.customers = accounts;
	}
}
