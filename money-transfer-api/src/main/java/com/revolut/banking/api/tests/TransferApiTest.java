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
		account1 = createAccount(account1, customer1.getCustomerId());

		Customer customer2 = new Customer();
		customer2.setCustomerId("souji0202");
		customer2.setFullName("Sai Soujanya");
		customer2.setAddress("157 Burges Road, E6 5BL, London");
		createCustomer(customer2);
		Account account2 = new Account();
		account2.setBalance(BigDecimal.valueOf(5000));
		account2 = createAccount(account2, customer2.getCustomerId());

		Transfer transfer = new Transfer();
		transfer.setTransferAmount(BigDecimal.valueOf(556.45));
		transfer.setSender(account1);
		transfer.setReceiver(account2);

		Response transferOutput = target("/transfers").request()
				.post(Entity.entity(transfer, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.OK.getStatusCode(), transferOutput.getStatus(), "Should return Status OK");
		LOG.info(transferOutput.readEntity(String.class));
	}
	
	@After
	public void deleteCustomers() {
		Customer customer1 = new Customer();
		customer1.setCustomerId("pradeepntg");
		Customer customer2 = new Customer();
		customer2.setCustomerId("souji0202");
		Response transferOutput = target("/customers/"+customer1.getCustomerId()).request().delete();
		LOG.info(transferOutput.readEntity(String.class));
		Response transferOutput2 = target("/customers/"+customer2.getCustomerId()).request().delete();
		LOG.info(transferOutput2.readEntity(String.class));
	}
	
	@Test
	public void testFailedMoneyTransfer() throws InvalidBalanceException {
		Customer customer1 = new Customer();
		customer1.setCustomerId("pradeepntg");
		customer1.setFullName("Pradeep Swamireddy");
		customer1.setAddress("45 Sheppard St, E16 4JX, London");
		createCustomer(customer1);
		Account account1 = new Account();
		account1.setBalance(BigDecimal.valueOf(10000));
		account1 = createAccount(account1, customer1.getCustomerId());

		Customer customer2 = new Customer();
		customer2.setCustomerId("souji0202");
		customer2.setFullName("Sai Soujanya");
		customer2.setAddress("157 Burges Road, E6 5BL, London");
		createCustomer(customer2);
		Account account2 = new Account();
		account2.setBalance(BigDecimal.valueOf(5000));
		account2 = createAccount(account2, customer2.getCustomerId());

		Transfer transfer2 = new Transfer();
		transfer2.setTransferAmount(BigDecimal.valueOf(11000));
		transfer2.setSender(account1);
		transfer2.setReceiver(account2);

		Response transferOutput2 = target("/transfers").request()
				.post(Entity.entity(transfer2, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), transferOutput2.getStatus(), "Should return Status Not Acceptable");
		LOG.info(transferOutput2.readEntity(String.class));
	}

	public void createCustomer(Customer customer) {
		Response custOutput = target("/customers").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		LOG.info(custOutput.readEntity(String.class));
	}

	public Account createAccount(Account account, String customerId) {
		StringBuilder accountUrl = new StringBuilder("/customers/");
		accountUrl.append(customerId).append("/accounts");
		Response accOutput = target(accountUrl.toString()).request()
				.post(Entity.entity(account, MediaType.APPLICATION_JSON));
		Account savedAccount = accOutput.readEntity(Account.class);
		LOG.info(savedAccount.toString());
		return savedAccount;
	}

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(TransferApi.class, CustomerApi.class);
	}
}
