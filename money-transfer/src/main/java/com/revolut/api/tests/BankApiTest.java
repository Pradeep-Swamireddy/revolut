package com.revolut.api.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.jboss.logging.Logger;
import org.junit.Test;

import com.revolut.api.BankApi;
import com.revolut.entities.Account;
import com.revolut.entities.Bank;
import com.revolut.entities.Customer;

public class BankApiTest extends JerseyTest {

	public static final Logger LOG = Logger.getLogger(BankApiTest.class);

	@Test
	public void testAddBank() {
		Bank bank = new Bank("B40", "HSBC BANK PLC", "Westfield Stratford City, Leyton Rd, London E15 1AA");
		Response output = target("/banks").request().post(Entity.entity(bank, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.OK.getStatusCode(), output.getStatus(), "Should return Status OK");
		LOG.info(output.readEntity(String.class));
	}

	@Test
	public void testAddCustomer() {
		Customer customer = new Customer("Pradeep", "Swamireddy", "52 Broad St ,Canning Town, London, E16 2HQ");
		Response custOutput = target("/banks/B40/customers").request()
				.post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.OK.getStatusCode(), custOutput.getStatus(), "Should return Status OK");
		LOG.info(custOutput.readEntity(String.class));
	}

	@Test
	public void testAddAccount() {
		Account account = new Account(42000);
		Response accountOutput = target("/banks/B40/customers/1/accounts").request()
				.post(Entity.entity(account, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.OK.getStatusCode(), accountOutput.getStatus(), "Should return Status OK");
		LOG.info(accountOutput.readEntity(String.class));
	}

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BankApi.class);
	}

}
