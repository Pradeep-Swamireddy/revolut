package com.revolut.main;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.PrintWriter;

import org.jboss.logging.Logger;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class RunTests {
	public static final Logger LOG = Logger.getLogger(RunTests.class);

	SummaryGeneratingListener listener = new SummaryGeneratingListener();

	public void runTests() {
		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
				.selectors(selectPackage("com.revolut.api.tests"))
				.selectors(selectPackage("com.revolut.repository.tests")).filters(includeClassNamePatterns(".*Test"))
				.build();
		Launcher launcher = LauncherFactory.create();
		launcher.discover(request);
		launcher.registerTestExecutionListeners(listener);
		launcher.execute(request);
	}

	public static void main(String[] args) throws Exception {
		RunTests testRunner = new RunTests();
		testRunner.runTests();
		TestExecutionSummary summary = testRunner.listener.getSummary();
		summary.printTo(new PrintWriter(System.out));
		summary.getFailures().forEach(f -> System.out.println(
				String.format("%s , %s", f.getTestIdentifier().getSource().get(), f.getException().getMessage())));
	}
}
