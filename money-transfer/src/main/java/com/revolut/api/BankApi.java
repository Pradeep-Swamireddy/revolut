package com.revolut.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.revolut.entities.Bank;
import com.revolut.service.BankService;
import com.revolut.service.BankServiceImpl;

@Path("/banks")
public class BankApi {
	public static final Logger LOG = Logger.getLogger(BankApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addBank(Bank bank) {
		BankService bankService = new BankServiceImpl();
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

	/*@POST
	@Path("/{bankCode}/customers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addCustomer(Customer customer, @PathParam("bankCode") String bankCode) {
		
	}
*/
}
