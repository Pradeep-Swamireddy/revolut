package com.revolut.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.revolut.entities.Bank;
import com.revolut.entities.Customer;
import com.revolut.repository.CustomerService;
import com.revolut.repository.CustomerServiceImpl;
import com.revolut.service.BankService;
import com.revolut.service.BankServiceImpl;

@Path("/banks")
public class BankApi {
	public static final Logger LOG = Logger.getLogger(BankApi.class);

	private BankService bankService = new BankServiceImpl();
	private CustomerService custService = new CustomerServiceImpl();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addBank(Bank bank) {
		LOG.info(bank.getBankName());
		Status statusCode;
		String responseMessage;
		if (bankService.addBank(bank)) {
			statusCode = Status.OK;
			responseMessage = String.format("Bank Name: %s, Code: %s created successfully", bank.getBankName(),
					bank.getCode());
		} else {
			statusCode = Status.EXPECTATION_FAILED;
			responseMessage = String.format("Failed to create Bank with Name: %s, Code: %s", bank.getBankName(),
					bank.getCode());
		}
		return Response.status(statusCode).entity(responseMessage).build();
	}

	@POST
	@Path("/{bankCode}/customers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addCustomer(Customer customer, @PathParam("bankCode") String bankCode) {
		Status statusCode;
		String responseMessage;
		Customer newCustomer = custService.addCustomer(customer, bankCode);
		if (newCustomer != null) {
			statusCode = Status.OK;
			responseMessage = String.format("Customer Name: %s, Customer Id: %s created successfully",
					newCustomer.getFirstName(), newCustomer.getId());
		} else {
			statusCode = Status.EXPECTATION_FAILED;
			responseMessage = String.format("Failed to create Customer with Name: %s in Bank: %s",
					customer.getFirstName(), bankCode);
		}
		return Response.status(statusCode).entity(responseMessage).build();
	}

}
