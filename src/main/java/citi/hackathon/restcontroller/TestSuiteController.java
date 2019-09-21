package citi.hackathon.restcontroller;

import java.time.Instant;
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
import citi.hackathon.entity.ReportEvent;
import citi.hackathon.entity.JunitReport;
import citi.hackathon.entity.BreakdownReport;
import citi.hackathon.entity.ResetPassword;
import citi.hackathon.entity.ResultString;
import citi.hackathon.entity.UpdateAccount;
import citi.hackathon.entity.UserHistoricalBreakdownReport;
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
	public ResponseEntity<Account> update_account_type(@RequestParam("userId") Integer userId) {
		if (userId < 0) {
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account(1, "admin", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "admin", "admin_nimda@email.com",
				"Admin", "Nimda", "Male", 27);
		LOG.info("Update Account: " + account.toString());
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@PutMapping("/accounts")
	public ResponseEntity<Account> update_admin_account(@RequestParam("userId") Integer userId,
			@RequestBody UpdateAccount updateAccount) {
		if (userId < 0) {
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account(1, "admin", updateAccount.getPassword(), "admin", updateAccount.getEmailAddress(),
				updateAccount.getFirstName(), updateAccount.getLastName(), updateAccount.getGender(),
				updateAccount.getAge());
		LOG.info("Update Account: " + account.toString());
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@PatchMapping("/accounts")
	public ResponseEntity<Account> update_user_account(@RequestParam("userId") Integer userId,
			@RequestBody UpdateAccount updateAccount) {
		if (userId < 0) {
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Account account = new Account(2, "peter", updateAccount.getPassword(), "user", updateAccount.getEmailAddress(),
				updateAccount.getFirstName(), updateAccount.getLastName(), updateAccount.getGender(),
				updateAccount.getAge());
		LOG.info("Update Account: " + account.toString());
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@DeleteMapping("/accounts")
	public ResponseEntity<ResultString> delete_account(@RequestParam("userId") Integer userId) {
		LOG.info("Deleted Account UserId: " + userId);
		if (userId < 0) {
			return new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResultString>(new ResultString("Account Deleted"), HttpStatus.OK);
	}

	@PostMapping("/accounts/reset-password")
	public ResponseEntity<ResultString> reset_account(@RequestParam("userId") Integer userId,
			@RequestBody ResetPassword resetPassword) {
		if (userId < 0) {
			return new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResultString>(new ResultString("Your password has been sent to your email"),
				HttpStatus.OK);
	}

	@GetMapping("/reports/demographic")
	public ResponseEntity<DemographicReport> get_demographic_report(@RequestParam("eventId") Integer eventId) {
		if (eventId < 0) {
			return new ResponseEntity<DemographicReport>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<Volunteer> volunteers = new ArrayList<Volunteer>();
		volunteers.add(new Volunteer(2, "Peter", "Johnson", "M", 19, "user"));
		volunteers.add(new Volunteer(3, "Adam", "Steven", "M", 42, "user"));
		volunteers.add(new Volunteer(5, "Crystal", "Sam", "F", 25, "user"));
		volunteers.add(new Volunteer(15, "Kylie", "Jade", "F", 33, "user"));
		DemographicReport demographicReport = new DemographicReport(1002, "Dog Shelter Cleaning",
				"2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4, "SPCA", 1, volunteers, "closed");
		LOG.info("Returning Demographic Report: " + demographicReport.toString());
		return new ResponseEntity<DemographicReport>(demographicReport, HttpStatus.OK);
	}

	@GetMapping("/reports/organization")
	public ResponseEntity<BreakdownReport> get_organization_breakdown_report(
			@RequestParam("organizerName") String organizerName) {
		if ("SPCC".equalsIgnoreCase(organizerName)) {
			return new ResponseEntity<BreakdownReport>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<ReportEvent> events = new ArrayList<ReportEvent>();
		List<Volunteer> volunteers = new ArrayList<Volunteer>();
		volunteers.add(new Volunteer(2, "Peter", "Johnson", "M", 19, "user"));
		volunteers.add(new Volunteer(3, "Adam", "Steven", "M", 42, "user"));
		volunteers.add(new Volunteer(5, "Crystal", "Sam", "F", 25, "user"));
		volunteers.add(new Volunteer(15, "Kylie", "Jade", "F", 33, "user"));
		events.add(new ReportEvent(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4,
				volunteers, 1, "closed"));
		events.add(new ReportEvent(1003, "Walk the Talk, Walk the Dogs", "2019-11-05T13:00:00Z", "2019-11-05T16:00:00Z",
				6, volunteers, 1, "open"));
		BreakdownReport organizationBreakdownReport = new BreakdownReport(events);
		LOG.info("Returning Org Breakdown Report: " + organizationBreakdownReport.toString());
		return new ResponseEntity<BreakdownReport>(organizationBreakdownReport, HttpStatus.OK);
	}

	@GetMapping("/reports/historical")
	public ResponseEntity<BreakdownReport> get_historial_report_for_admin(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		BreakdownReport historicalBreakdownReport = null;
		if (fromDate == null || toDate == null) {
			List<ReportEvent> events = new ArrayList<ReportEvent>();
			List<Volunteer> volunteers = new ArrayList<Volunteer>();
			volunteers.add(new Volunteer(2, "Peter", "Johnson", "M", 19, "user"));
			volunteers.add(new Volunteer(3, "Adam", "Steven", "M", 42, "user"));
			volunteers.add(new Volunteer(5, "Crystal", "Sam", "F", 25, "user"));
			volunteers.add(new Volunteer(15, "Kylie", "Jade", "F", 33, "user"));
			events.add(new ReportEvent(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4,
					volunteers, 1, "closed", "SPCA"));
			events.add(new ReportEvent(1003, "Beach Cleaning", "2019-01-02T01:00:00Z", "2019-01-02T04:00:00Z", 4,
					volunteers, 2, "open", "NEA"));
			events.add(new ReportEvent(1004, "Zoo Outing", "2019-07-02T01:00:00Z", "2019-07-02T08:00:00Z", 3,
					volunteers, 3, "open", "YMCA"));
			historicalBreakdownReport = new BreakdownReport(events);
			LOG.info("Returning All Admin Historical Breakdown Report: " + historicalBreakdownReport.toString());
		} else {
			List<ReportEvent> events = new ArrayList<ReportEvent>();
			List<Volunteer> volunteers = new ArrayList<Volunteer>();
			volunteers.add(new Volunteer(2, "Peter", "Johnson", "M", 19, "user"));
			volunteers.add(new Volunteer(3, "Adam", "Steven", "M", 42, "user"));
			volunteers.add(new Volunteer(5, "Crystal", "Sam", "F", 25, "user"));
			volunteers.add(new Volunteer(15, "Kylie", "Jade", "F", 33, "user"));
			events.add(new ReportEvent(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4,
					volunteers, 1, "closed", "SPCA"));
			events.add(new ReportEvent(1003, "Beach Cleaning", "2019-01-02T01:00:00Z", "2019-01-02T04:00:00Z", 4,
					volunteers, 2, "open", "NEA"));
			historicalBreakdownReport = new BreakdownReport(events);
			LOG.info("Returning from {} to {} Admin Historical Breakdown Report: " + historicalBreakdownReport.toString());
		}
		return new ResponseEntity<BreakdownReport>(historicalBreakdownReport, HttpStatus.OK);
	}

	@GetMapping("/reports/user-historical")
	public ResponseEntity<UserHistoricalBreakdownReport> get_user_historial_report(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		UserHistoricalBreakdownReport userHistoricalBreakdownReport = null;
		if (userId == null) {
			userHistoricalBreakdownReport = new UserHistoricalBreakdownReport(0, 0, "2018-12-25", "2019-08-07",
					new ArrayList<ReportEvent>());
			LOG.info("Returning no result: " + userHistoricalBreakdownReport.toString());
		} else if (fromDate == null || toDate == null) {
			List<ReportEvent> reportEvents = new ArrayList<ReportEvent>();
			reportEvents.add(new ReportEvent(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z",
					"2018-12-28T12:00:00Z", 2, "SPCA", 1, "closed"));
			reportEvents.add(new ReportEvent(1005, "Walk the Talk, Walk the Dogs", "2019-08-02T01:00:00Z",
					"2019-08-02T04:00:00Z", 3, "SPCA", 1, "open"));
			reportEvents.add(new ReportEvent(1004, "Zoo Outing", "2019-07-02T01:00:00Z",
					"2019-07-02T08:00:00Z", 7, "YMCA", 3, "open"));
			userHistoricalBreakdownReport = new UserHistoricalBreakdownReport(2, 5, "2018-12-25", "2019-08-07",
					reportEvents);
			LOG.info("Returning all of userId [{}] historical report: " + userHistoricalBreakdownReport.toString(), userId);
		} else {
			List<ReportEvent> reportEvents = new ArrayList<ReportEvent>();
			reportEvents.add(new ReportEvent(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z",
					"2018-12-28T12:00:00Z", 2, "SPCA", 1, "confirmed"));
			reportEvents.add(new ReportEvent(1005, "Walk the Talk, Walk the Dogs", "2019-08-02T01:00:00Z",
					"2019-08-02T04:00:00Z", 3, "SPCA", 1, "closed"));
			userHistoricalBreakdownReport = new UserHistoricalBreakdownReport(2, 5, "2018-12-25", "2019-08-07",
					reportEvents);
			LOG.info("Returning userId [{}] from {} to {} historical report:" + userHistoricalBreakdownReport.toString(), userId, fromDate, toDate);
		}
		return new ResponseEntity<UserHistoricalBreakdownReport>(userHistoricalBreakdownReport, HttpStatus.OK);
	}

}
