package com.revolut.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.revolut.entities.Account;

@Path("/money")
public class AccountEndpoint {
	Logger log = Logger.getLogger(AccountEndpoint.class);

	@POST
	@Path("/account")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addAccount(Account account) {
		log.info(account);
		return Response.status(Response.Status.OK).entity("Account created successfully").build();
	}

	@GET
	@Path("/account")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAccount() {
		return "Account 1";
	}
}
