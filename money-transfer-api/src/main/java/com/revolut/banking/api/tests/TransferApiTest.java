package com.revolut.banking.api.tests;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.api.CustomerApi;
import com.revolut.banking.api.TransferApi;
import com.revolut.banking.entities.Account;
import com.revolut.banking.entities.Customer;
import com.revolut.banking.entities.Transfer;
import com.revolut.banking.exceptions.InvalidBalanceException;

public class TransferApiTest extends JerseyTest {

	public static final Logger LOG = LoggerFactory.getLogger(TransferApiTest.class);

	@Test
	public void testTransferMoney() throws InvalidBalanceException {
		Customer customer1 = new Customer();
		customer1.setCustomerId("pradeepntg");
		customer1.setFullName("Pradeep Swamireddy");
		customer1.setAddress("45 Sheppard St, E16 4JX, London");
		createCustomer(customer1);
		Account account1 = new Account();
		account1.setBalance(BigDecimal.valueOf(10000));
		createAccount(account1, customer1.getCustomerId());

		Customer customer2 = new Customer();
		customer2.setCustomerId("souji0202");
		customer2.setFullName("Sai Soujanya");
		customer2.setAddress("157 Burges Road, E6 5BL, London");
		createCustomer(customer2);
		Account account2 = new Account();
		account2.setBalance(BigDecimal.valueOf(5000));
		createAccount(account2, customer2.getCustomerId());

		Transfer transfer = new Transfer();
		transfer.setTransferAmount(BigDecimal.valueOf(1000));
		transfer.setSender(account1);
		transfer.setReceiver(account2);

		Response transferOutput = target("/transfers").request()
				.post(Entity.entity(transfer, MediaType.APPLICATION_JSON));
		LOG.info(transferOutput.readEntity(String.class));
	}

	public void createCustomer(Customer customer) {
		Response custOutput = target("/customers").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		LOG.info(custOutput.readEntity(String.class));
	}

	public void createAccount(Account account, String customerId) {
		StringBuilder accountUrl = new StringBuilder("/customers/");
		accountUrl.append(customerId).append("/accounts");
		Response accOutput = target(accountUrl.toString()).request()
				.post(Entity.entity(account, MediaType.APPLICATION_JSON));
		Account savedAccount = accOutput.readEntity(Account.class);
		LOG.info(savedAccount.toString());
	}

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(TransferApi.class, CustomerApi.class);
	}
}
