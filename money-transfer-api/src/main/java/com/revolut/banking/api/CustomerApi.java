package com.revolut.banking.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.entities.Account;
import com.revolut.banking.entities.Customer;
import com.revolut.banking.service.AccountService;
import com.revolut.banking.service.AccountServiceFactory;
import com.revolut.banking.service.CustomerService;
import com.revolut.banking.service.CustomerServiceFactory;

@Path("/customers")
public class CustomerApi {
	public static final Logger LOG = LoggerFactory.getLogger(CustomerApi.class);
	private CustomerService customerService = CustomerServiceFactory.getCustomerService();
	private AccountService accountService = AccountServiceFactory.getAccountService();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addCustomer(Customer customer) {
		Status statusCode = Status.NOT_ACCEPTABLE;
		String responseMessage = "Failed to create Customer";
		try {
			Customer newCustomer = customerService.addCustomer(customer);
			if (newCustomer != null) {
				statusCode = Status.OK;
				responseMessage = String.format("Customer : %s created with Customer Id: %s", newCustomer.getFullName(),
						newCustomer.getCustomerId());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return Response.status(statusCode).entity(responseMessage).build();
	}

	@Path("/{customerId}/accounts")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Account addAccount(Account account, @PathParam("customerId") String customerId) {
		Account responseAccount = account;
		try {
			Account newAccount = accountService.addAccount(account, customerId);
			if (newAccount != null) {
				responseAccount = newAccount;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return responseAccount;
	}
	
	@Path("/{customerId}/accounts/{accountId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("customerId") String customerId,@PathParam("accountId") long accountId) {
		return accountService.findAccount(accountId);
	}
	
	@Path("/{customerId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("customerId") String customerId) {
		return customerService.findCustomer(customerId);
	}
	
	@Path("/{customerId}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteCustomer(@PathParam("customerId") String customerId) {
		Status statusCode = Status.NOT_ACCEPTABLE;
		String responseMessage = "Failed to create Account";
		try {
			if (customerService.deleteCustomer(customerId)) {
				statusCode = Status.OK;
				responseMessage = "Customer deleted successfully: " + customerId;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return Response.status(statusCode).entity(responseMessage).build();
	}

}
