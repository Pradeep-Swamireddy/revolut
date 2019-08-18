package com.revolut.banking.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferApp {

	public static final Logger LOG = LoggerFactory.getLogger(TransferApp.class);

	public static final int SERVER_PORT = 9081;

	public static void main(String[] args) throws Exception {
		Server jettyServer = new Server(SERVER_PORT);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/");

		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.revolut.banking.api");
		/*
		 * ServletHolder h2Servlet= context.addServlet(WebServlet.class, "/console/*");
		 * h2Servlet.setName("H2Console"); h2Servlet.setInitParameter("webAllowOthers",
		 * ""); h2Servlet.setInitParameter("trace", "");
		 */
		/*org.h2.tools.Server server = org.h2.tools.Server.createTcpServer().start();
		LOG.info("Server started and connection is open.");
		LOG.info("URL: jdbc:h2:" + server.getURL() + "/mem:" + "testdb");
		LOG.info("Server started with port 9081");*/
		jettyServer.start();
		jettyServer.join();

	}
}
