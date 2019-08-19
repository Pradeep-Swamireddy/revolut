package com.revolut.banking.api.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.api.CustomerApi;
import com.revolut.banking.entities.Account;
import com.revolut.banking.entities.Customer;
import com.revolut.banking.exceptions.InvalidBalanceException;

public class CustomerApiTest extends JerseyTest {
	public static final Logger LOG = LoggerFactory.getLogger(CustomerApiTest.class);

	@Test
	public void testAddCustomer() {
		LOG.info("========================Test to create customer=======================");
		Customer customer = createTestCustomer();
		Response custOutput = target("/customers").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.OK.getStatusCode(), custOutput.getStatus(), "Should return Status OK");
		LOG.info(custOutput.readEntity(String.class));
	}

	@Test
	public void testGetCustomer() {
		LOG.info("==========================Test to get customer=========================");
		Customer customer = createTestCustomer();
		Response custOutput = target("/customers").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		LOG.info(custOutput.readEntity(String.class));
		Response custOutput2 = target("/customers/harisuji566").request().get();
		assertEquals(Response.Status.OK.getStatusCode(), custOutput2.getStatus(), "Should return Status OK");
		LOG.info("Retrieved Customer successfully- {}", custOutput2.readEntity(String.class));
	}

	@Test
	public void testAddAccount() throws InvalidBalanceException {
		LOG.info("==========================Test to Add Account=========================");
		Customer customer = createTestCustomer();
		Response custOutput = target("/customers").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		LOG.info(custOutput.readEntity(String.class));
		Account account = new Account();
		account.setBalance(BigDecimal.valueOf(10000));
		Response accOutput = target("/customers/harisuji566/accounts").request()
				.post(Entity.entity(account, MediaType.APPLICATION_JSON));
		account = accOutput.readEntity(Account.class);
		assertEquals(Response.Status.OK.getStatusCode(), accOutput.getStatus(), "Should return Status OK");
		LOG.info(account.toString());
	}

	@Test
	public void testGetAccount() throws InvalidBalanceException {
		LOG.info("==========================Test to Get Account=========================");
		Customer customer = createTestCustomer();
		Response custOutput = target("/customers").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		LOG.info(custOutput.readEntity(String.class));
		Account account = new Account();
		account.setBalance(BigDecimal.valueOf(10000));
		Response accOutput = target("/customers/harisuji566/accounts").request()
				.post(Entity.entity(account, MediaType.APPLICATION_JSON));
		account = accOutput.readEntity(Account.class);
		LOG.info(account.toString());
		Response accOutput2 = target("/customers/harisuji566/accounts/" + account.getAccountNo()).request().get();
		assertEquals(Response.Status.OK.getStatusCode(), accOutput2.getStatus(), "Should return Status OK");
		Account retrievedAccount = accOutput2.readEntity(Account.class);
		LOG.info("Retrieved Account successfully- {}", retrievedAccount.toString());
	}

	@After
	public void deleteTestCustomer() {
		Response custOutput2 = target("/customers/harisuji566").request().delete();
		assertEquals(Response.Status.OK.getStatusCode(), custOutput2.getStatus(), "Should return Status OK");
		LOG.info(custOutput2.readEntity(String.class));
	}

	private Customer createTestCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId("harisuji566");
		customer.setFullName("Hari Swamireddy");
		customer.setAddress("28-1231, New Balaji Colony, Bangalore");
		return customer;
	}

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(CustomerApi.class);
	}
}
