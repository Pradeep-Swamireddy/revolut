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
import com.revolut.entities.Bank;
import com.revolut.entities.Customer;

public class BankApiTest extends JerseyTest {

	public static final Logger LOG = Logger.getLogger(BankApiTest.class);

	@Test
	public void testAddBank() {
		Bank bank = new Bank();
		bank.setCode("B40");
		bank.setBankName("HSBC");
		bank.setAddress("Canary Wharf, London");
		Response output = target("/banks").request().post(Entity.entity(bank, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.OK.getStatusCode(), output.getStatus(), "Should return Status OK");
		LOG.info(output.readEntity(String.class));
	}
	
	@Test
	public void testAddCustomer() {
		Customer customer = new Customer();
		Bank bank = new Bank();
		bank.setCode("B40");
		customer.setBank(bank);
		customer.setFirstName("Pradeep");
		customer.setLastName("Swamireddy");
		customer.setAddress("Canning Town, London");
		Response output = target("/banks/B40/customers").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.OK.getStatusCode(), output.getStatus(), "Should return Status OK");
		LOG.info(output.readEntity(String.class));
	}


	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BankApi.class);
	}
}
