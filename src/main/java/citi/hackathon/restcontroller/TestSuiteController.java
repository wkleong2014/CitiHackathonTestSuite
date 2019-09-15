package citi.hackathon.restcontroller;

import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import citi.hackathon.entity.ResultString;
import citi.hackathon.entity.UpdateAccount;

@RestController
public class TestSuiteController {
	private static final Logger LOG = LoggerFactory.getLogger(TestSuiteController.class);

	@RequestMapping("/test/authentication")
	public JunitReport testAuthentication(@RequestParam(value = "url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentAuthenticationUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonAuthenticationTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(),
				result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}

	@RequestMapping("/test/event")
	public JunitReport testEvent(@RequestParam(value = "url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentEventUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonEventTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(),
				result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}

	@RequestMapping("/test/report")
	public JunitReport testReport(@RequestParam(value = "url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentReportUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonReportTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(),
				result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}

	@RequestMapping("/test/communicator")
	public JunitReport testCommunicator(@RequestParam(value = "url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setCurrentCommunicatorUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonCommunicatorTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(),
				result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}

	@GetMapping("/accounts")
	public Account get_account(@RequestParam("userId") Integer userId) {
		Account account = null;
		if (userId == 1) {
			account = new Account(1, "admin", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "admin", "admin_nimda@email.com",
					"Admin", "Nimda", true);
		} else if (userId == 2) {
			account = new Account(2, "peter", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "user", "peter_johnson@email.com",
					"Peter", "Johnson", false);
		}
		LOG.info("Returning Account: " + account.toString());
		return account;
	}

	@PostMapping("/accounts")
	public Account create_account(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("accountType") String accountType, @RequestParam("emailAddress") String emailAddress,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		Account account = null;
		if ("admin".equals(accountType)) {
			account = new Account(1, username, password, accountType, emailAddress, firstName, lastName, true);
		} else {
			account = new Account(2, username, password, accountType, emailAddress, firstName, lastName, false);
		}
		LOG.info("Create Account: " + account.toString());
		return account;
	}

	@PutMapping("/accounts")
	public Account update_admin_account(@RequestParam("userId") Integer userId,
			@RequestBody UpdateAccount updateAccount) {
		Account account = new Account(1, "admin", updateAccount.getPassword(), "admin", updateAccount.getEmailAddress(),
				updateAccount.getFirstName(), updateAccount.getLastName(), true);
		LOG.info("Update Account: " + account.toString());
		return account;
	}

	@PatchMapping("/accounts")
	public Account update_user_account(@RequestParam("userId") Integer userId,
			@RequestBody UpdateAccount updateAccount) {
		Account account = new Account(2, "peter", updateAccount.getPassword(), "user", updateAccount.getEmailAddress(),
				updateAccount.getFirstName(), updateAccount.getLastName(), false);
		LOG.info("Update Account: " + account.toString());
		return account;
	}

	@DeleteMapping("/accounts")
	public Account delete_account(@RequestParam("userId") Integer userId) {
		Account account = null;
		if (userId == 1) {
			account = new Account(userId, "admin", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "admin", "admin_nimda@email.com",
					"Admin", "Nimda", true);
		} else if (userId == 2) {
			account = new Account(userId, "peter", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "user",
					"peter_johnson@email.com", "Peter", "Johnson", false);
		}
		LOG.info("Deleted Account: " + account.toString());
		return account;
	}
	
	@PostMapping("/accounts/reset")
	public ResultString reset_account(@RequestParam("userId") Integer userId, @RequestParam("email") String email) {
		return new ResultString("Your password has been sent to your email");
	}

}
