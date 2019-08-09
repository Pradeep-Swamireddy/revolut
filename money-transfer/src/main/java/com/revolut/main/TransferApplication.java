package com.revolut.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.logging.Logger;

public class TransferApplication {

	public static final Logger LOGGER = Logger.getLogger(TransferApplication.class);

	public static void main(String[] args) throws Exception {
		Server jettyServer = new Server(9081);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/");

		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/api/*");
		jerseyServlet.setInitOrder(0);
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.revolut.endpoints");

		jettyServer.start();
		jettyServer.join();
		LOGGER.info("Server started with port 9081");
	}

}
