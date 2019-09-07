package citi.hackathon.restcontroller;

import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import citi.hackathon.CitiHackathonAuthenticationTestSuite;
import citi.hackathon.CitiHackathonCommunicatorTestSuite;
import citi.hackathon.CitiHackathonEventTestSuite;
import citi.hackathon.CitiHackathonReportTestSuite;
import citi.hackathon.config.SpringConfig;
import citi.hackathon.entity.Account;
import citi.hackathon.entity.JunitReport;
import citi.hackathon.entity.Sample;

@RestController
public class TestSuiteController {
	private static final Logger LOG = LoggerFactory.getLogger(TestSuiteController.class);
	
	@RequestMapping("/test/authentication")
	public JunitReport testAuthentication(@RequestParam(value="url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentAuthenticationUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonAuthenticationTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(), result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}
	
	@RequestMapping("/test/event")
	public JunitReport testEvent(@RequestParam(value="url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentEventUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonEventTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(), result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}
	
	@RequestMapping("/test/report")
	public JunitReport testReport(@RequestParam(value="url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentReportUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonReportTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(), result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}
	
	@RequestMapping("/test/communicator")
	public JunitReport testCommunicator(@RequestParam(value="url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentCommunicatorUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonCommunicatorTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(), result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}
	
	@RequestMapping("/sample")
	public Sample sample() {
		return new Sample("Sample Username", "Sample Password");
	}
	
	@RequestMapping("/account")
	public Account test_account(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("accountType") String accountType,
			@RequestParam("emailAddress") String emailAddress,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName
			) {
		Account account = new Account(1, username, password, accountType, emailAddress, firstName, lastName);
		LOG.info("Returning Account: " + account.toString());
		return account;
	}
	
	@PostMapping("/sample_with_request_body")
	public Sample sample_with_request_body(@RequestBody Sample sample) {
		if (sample == null) {
			return sample;
		}
		sample.setUsername("updated_username");
		sample.setPassword("updated_password");
		return sample;
	}
	
	
}
