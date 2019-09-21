package citi.hackathon.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import citi.hackathon.entity.DemographicReport;
import citi.hackathon.entity.Event;
import citi.hackathon.entity.JunitReport;
import citi.hackathon.entity.OrganizationBreakdownReport;
import citi.hackathon.entity.ResetPassword;
import citi.hackathon.entity.ResultString;
import citi.hackathon.entity.UpdateAccount;
import citi.hackathon.entity.Volunteer;

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
					"Admin", "Nimda", "Male", 33);
		} else if (userId == 2) {
			account = new Account(2, "peter", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "user", "peter_johnson@email.com",
					"Peter", "Johnson", "Male", 33);
		}
		LOG.info("Returning Account: " + account.toString());
		return account;
	}

	@PostMapping("/accounts")
	public Account create_account(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("accountType") String accountType, @RequestParam("emailAddress") String emailAddress,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("gender") String gender, @RequestParam("age") Integer age) {
		Account account = null;
		if ("admin".equals(accountType)) {
			account = new Account(1, username, password, accountType, emailAddress, firstName, lastName, gender, age);
		} else {
			account = new Account(2, username, password, accountType, emailAddress, firstName, lastName, gender, age);
		}
		LOG.info("Create Account: " + account.toString());
		return account;
	}

	@PutMapping("/accounts/account-type")
	public ResponseEntity update_account_type(@RequestParam("userId") Integer userId) {
		if (userId < 0) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account(1, "admin", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "admin", "admin_nimda@email.com",
				"Admin", "Nimda", "Male", 27);
		LOG.info("Update Account: " + account.toString());
		return new ResponseEntity(account, HttpStatus.OK);
	}

	@PutMapping("/accounts")
	public ResponseEntity update_admin_account(@RequestParam("userId") Integer userId,
			@RequestBody UpdateAccount updateAccount) {
		if (userId < 0) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account(1, "admin", updateAccount.getPassword(), "admin", updateAccount.getEmailAddress(),
				updateAccount.getFirstName(), updateAccount.getLastName(), updateAccount.getGender(),
				updateAccount.getAge());
		LOG.info("Update Account: " + account.toString());
		return new ResponseEntity(account, HttpStatus.OK);
	}

	@PatchMapping("/accounts")
	public ResponseEntity update_user_account(@RequestParam("userId") Integer userId,
			@RequestBody UpdateAccount updateAccount) {
		if (userId < 0) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account(2, "peter", updateAccount.getPassword(), "user", updateAccount.getEmailAddress(),
				updateAccount.getFirstName(), updateAccount.getLastName(), updateAccount.getGender(),
				updateAccount.getAge());
		LOG.info("Update Account: " + account.toString());
		return new ResponseEntity(account, HttpStatus.OK);
	}

	@DeleteMapping("/accounts")
	public ResponseEntity delete_account(@RequestParam("userId") Integer userId) {
		LOG.info("Deleted Account UserId: " + userId);
		if (userId < 0) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(new ResultString("Account Deleted"), HttpStatus.OK);
	}

	@PostMapping("/accounts/reset-password")
	public ResponseEntity reset_account(@RequestParam("userId") Integer userId,
			@RequestBody ResetPassword resetPassword) {
		if (userId < 0) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(new ResultString("Your password has been sent to your email"), HttpStatus.OK);
	}

	@GetMapping("/reports/demographic")
	public DemographicReport get_demographic_report(@RequestParam("eventId") Integer eventId) {
		List<Volunteer> volunteers = new ArrayList<Volunteer>();
		volunteers.add(new Volunteer(2, "peter", "Johnson", "M", 19, "user"));
		volunteers.add(new Volunteer(3, "Adam", "Steven", "M", 42, "user"));
		volunteers.add(new Volunteer(5, "Crystal", "Sam", "F", 25, "user"));
		volunteers.add(new Volunteer(15, "Kylie", "Jade", "F", 33, "user"));
		DemographicReport demographicReport = new DemographicReport(1002, "Dog Shelter Cleaning",
				"2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4, "SPCA", "Animals", volunteers);
		LOG.info("Returning Demographic Report: " + demographicReport.toString());
		return demographicReport;
	}

	@GetMapping("/reports/organization-breakdown")
	public OrganizationBreakdownReport get_organization_breakdown_report(@RequestParam("eventId") Integer eventId) {
		List<Event> events = new ArrayList<Event>();
		events.add(new Event(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4, "Animals",
				"closed"));
		events.add(new Event(1003, "Walk the Talk, Walk the Dogs", "2019-11-05T13:00:00Z", "2019-11-05T16:00:00Z", 6,
				"Animals", "open"));
		OrganizationBreakdownReport organizationBreakdownReport = new OrganizationBreakdownReport(events);
		LOG.info("Returning Org Breakdown Report: " + organizationBreakdownReport.toString());
		return organizationBreakdownReport;
	}

}
