package com.revolut.banking.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.banking.entities.Transfer;
import com.revolut.banking.service.TransferService;
import com.revolut.banking.service.TransferServiceFactory;

@Path("/transfers")
public class TransferApi {

	public static final Logger LOG = LoggerFactory.getLogger(TransferApi.class);
	private TransferService transferService = TransferServiceFactory.getCustomerService();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response transferMoney(Transfer transfer) {
		LOG.info("{}", transfer);
		Status statusCode = Status.NOT_ACCEPTABLE;
		StringBuilder responseMessage = new StringBuilder("Transfer unsuccessful. ");
		try {
			Transfer savedTransfer = transferService.transferMoney(transfer);
			if (savedTransfer != null) {
				statusCode = Status.OK;
				responseMessage = new StringBuilder("Amount: ").append(savedTransfer.getTransferAmount())
						.append(" transferred successfully from sender id: ").append(savedTransfer.getSender().getAccountNo())
						.append(" and receiver id: ").append(savedTransfer.getReceiver().getAccountNo());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			responseMessage.append(e.getMessage());
		}
		return Response.status(statusCode).entity(responseMessage.toString()).build();
	}
}
