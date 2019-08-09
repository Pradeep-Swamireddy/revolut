package com.revolut.endpoints;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.revolut.entities.Account;

public class AccountEndpointTest extends JerseyTest {

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(AccountEndpoint.class);
	}

	@Test
	public void testAddAccount() {
		Account account = new Account(10, "Pradeep", 4260000);
		System.out.println(account.toString());
		Response output = target("/money/account").request()
				.post(Entity.entity(account, MediaType.APPLICATION_JSON));
		assertEquals("Should return Status OK", Response.Status.OK.getStatusCode(), output.getStatus());
		System.out.println();
	}

	@Test
	public void testGetAccount() {
		Response output = target("/money/account").request().get();
		assertEquals("Should return Status OK", Response.Status.OK.getStatusCode(), output.getStatus());
	}

}
