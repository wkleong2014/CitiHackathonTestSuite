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
import citi.hackathon.entity.AddVolunteer;
import citi.hackathon.entity.AllEvents;
import citi.hackathon.entity.BreakdownReport;
import citi.hackathon.entity.Category;
import citi.hackathon.entity.CategoryArray;
import citi.hackathon.entity.CategoryRequestBody;
import citi.hackathon.entity.CreateAccountRequestBody;
import citi.hackathon.entity.DemographicReport;
import citi.hackathon.entity.Event;
import citi.hackathon.entity.EventRequestBody;
import citi.hackathon.entity.Feedback;
import citi.hackathon.entity.FeedbackArray;
import citi.hackathon.entity.FeedbackRequestBody;
import citi.hackathon.entity.JunitReport;
import citi.hackathon.entity.ReportEvent;
import citi.hackathon.entity.ResetPassword;
import citi.hackathon.entity.ResultString;
import citi.hackathon.entity.UpdateAccount;
import citi.hackathon.entity.UserHistoricalBreakdownReport;
import citi.hackathon.entity.Volunteer;
import citi.hackathon.entity.VolunteerRequestBody;

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
	public Account create_account(@RequestBody CreateAccountRequestBody request) {
		Account account = null;
		if ("admin".equals(request.getAccountType())) {
			account = new Account(1, request.getUsername(), request.getPassword(), request.getAccountType(),
					request.getEmailAddress(), request.getFirstName(), request.getLastName(), request.getGender(),
					request.getAge());
		} else {
			account = new Account(2, request.getUsername(), request.getPassword(), request.getAccountType(),
					request.getEmailAddress(), request.getFirstName(), request.getLastName(), request.getGender(),
					request.getAge());
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
			LOG.info("Returning from {} to {} Admin Historical Breakdown Report: "
					+ historicalBreakdownReport.toString());
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
			reportEvents.add(new ReportEvent(1004, "Zoo Outing", "2019-07-02T01:00:00Z", "2019-07-02T08:00:00Z", 7,
					"YMCA", 3, "open"));
			userHistoricalBreakdownReport = new UserHistoricalBreakdownReport(2, 5, "2018-12-25", "2019-08-07",
					reportEvents);
			LOG.info("Returning all of userId [{}] historical report: " + userHistoricalBreakdownReport.toString(),
					userId);
		} else {
			List<ReportEvent> reportEvents = new ArrayList<ReportEvent>();
			reportEvents.add(new ReportEvent(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z",
					"2018-12-28T12:00:00Z", 2, "SPCA", 1, "confirmed"));
			reportEvents.add(new ReportEvent(1005, "Walk the Talk, Walk the Dogs", "2019-08-02T01:00:00Z",
					"2019-08-02T04:00:00Z", 3, "SPCA", 1, "closed"));
			userHistoricalBreakdownReport = new UserHistoricalBreakdownReport(2, 5, "2018-12-25", "2019-08-07",
					reportEvents);
			LOG.info(
					"Returning userId [{}] from {} to {} historical report:" + userHistoricalBreakdownReport.toString(),
					userId, fromDate, toDate);
		}
		return new ResponseEntity<UserHistoricalBreakdownReport>(userHistoricalBreakdownReport, HttpStatus.OK);
	}

	@PostMapping("/events")
	public Event create_event(@RequestBody EventRequestBody request) {
		Event event = new Event(1002, request.getEventName(), request.getStartDateTime(), request.getEndDateTime(),
				request.getOrganizerName(), request.getCategoryId(), request.getDescription(),
				request.getMaxParticipants(), request.getMinParticipants(), request.getEventStatus());
		LOG.info("Create Event: " + event.toString());
		return event;
	}

	@DeleteMapping("/events")
	public ResponseEntity<ResultString> delete_event(@RequestParam("eventId") Integer eventId) {
		LOG.info("Deleted Event Id: " + eventId);
		if (eventId < 0) {
			return new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResultString>(new ResultString("Event Deleted"), HttpStatus.OK);
	}

	@GetMapping("/events")
	public ResponseEntity get_event(@RequestParam(value = "eventId", required = false) Integer eventId,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "from_time", required = false) String fromTime,
			@RequestParam(value = "to_time", required = false) String toTime,
			@RequestParam(value = "categoryId", required = false) Integer categoryId) {
		ResponseEntity response = null;
		if (eventId == null && fromTime == null && toTime == null && search == null && categoryId == null) {
			List<Event> events = new ArrayList<>();
			events.add(new Event(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", "SPCA",
					1, "Dog event", 50, 10, "open"));
			events.add(new Event(1003, "National Day", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", "NDP", 1,
					"National Day event", 50, 10, "open"));
			AllEvents allEvent = new AllEvents(events);
			response = new ResponseEntity<AllEvents>(allEvent, HttpStatus.OK);
			LOG.info("Returning Event: " + allEvent.toString());
		} else if (fromTime == null && toTime == null && search == null && categoryId == null && eventId != null
				&& eventId < 0) {
			response = new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (fromTime == null && toTime == null && search == null && categoryId == null && eventId != null
				&& eventId >= 0) {
			response = new ResponseEntity<Event>(new Event(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z",
					"2018-12-28T12:00:00Z", "SPCA", 1, "Dog event", 50, 10, "open"), HttpStatus.OK);
			LOG.info("Returning Event: " + response.getBody().toString());
		}

		if (search != null && fromTime == null && toTime == null && categoryId == null && eventId == null) {
			List<Event> events = new ArrayList<>();
			events.add(new Event(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4,
					"SPCA", 1, "Dog event", 50, 10, "open"));
			events.add(new Event(1003, "Dog Shelter Cleaning 2", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4,
					"SPCA", 1, "Dog event Round 2", 50, 10, "open"));
			AllEvents allEvent = new AllEvents(events);
			response = new ResponseEntity<AllEvents>(allEvent, HttpStatus.OK);
			LOG.info("Returning Search Events: " + allEvent.toString());
		}

		if (fromTime != null && toTime != null && search == null && categoryId == null && eventId == null) {
			List<Event> events = new ArrayList<>();
			events.add(new Event(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4,
					"SPCA", 1, "Dog event", 50, 10, "open"));
			events.add(new Event(1003, "Dog Shelter Cleaning 2", "2018-12-28T10:00:00Z", "2018-12-29T12:00:00Z", 4,
					"SPCA", 1, "Dog event", 50, 10, "open"));
			AllEvents allEvent = new AllEvents(events);
			response = new ResponseEntity<AllEvents>(allEvent, HttpStatus.OK);
			LOG.info("Returning Time Searched Events: " + allEvent.toString());
		}

		if (categoryId != null && fromTime == null && toTime == null && search == null && eventId == null) {
			List<Event> events = new ArrayList<>();
			events.add(new Event(1002, "Dog Shelter Cleaning", "2018-12-28T10:00:00Z", "2018-12-28T12:00:00Z", 4,
					"SPCA", 1, "Dog event", 50, 10, "open"));
			events.add(new Event(1003, "Dog Shelter Cleaning 2", "2018-12-28T10:00:00Z", "2018-12-29T12:00:00Z", 4,
					"SPCA", 1, "Dog event", 50, 10, "open"));
			AllEvents allEvent = new AllEvents(events);
			response = new ResponseEntity<AllEvents>(allEvent, HttpStatus.OK);
			LOG.info("Returning Category Searched Events: " + allEvent.toString());
		}

		if (response == null) {
			response = new ResponseEntity<ResultString>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@PutMapping("/events")
	public ResponseEntity<Event> update_event(@RequestParam("eventId") Integer eventId,
			@RequestBody EventRequestBody request) {
		if (eventId < 0) {
			return new ResponseEntity<Event>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Event event = new Event(1002, request.getEventName(), request.getStartDateTime(), request.getEndDateTime(),
				request.getOrganizerName(), request.getCategoryId(), request.getDescription(),
				request.getMaxParticipants(), request.getMinParticipants(), request.getEventStatus());
		LOG.info("Update Event: " + event.toString());
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}

	@PostMapping("/categories")
	public Category create_category(@RequestBody CategoryRequestBody request) {
		Category category = new Category(1, request.getCategory());
		LOG.info("Create Category: " + category.toString());
		return category;
	}

	@GetMapping("/categories")
	public CategoryArray get_categories() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1, "Animals"));
		categories.add(new Category(2, "Environment"));
		CategoryArray categoriesArray = new CategoryArray(categories);
		return categoriesArray;
	}

	@PutMapping("/categories")
	public ResponseEntity<Category> update_categories(@RequestParam("categoryId") Integer categoryId,
			@RequestBody CategoryRequestBody request) {
		if (categoryId < 0) {
			return new ResponseEntity<Category>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Category category = new Category(1, request.getCategory());
		LOG.info("Update Category: " + category.toString());
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@DeleteMapping("/categories")
	public ResponseEntity<ResultString> delete_category(@RequestParam("categoryId") Integer categoryId) {
		LOG.info("Deleted Category Id: " + categoryId);
		if (categoryId < 0) {
			return new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResultString>(new ResultString("Category deleted"), HttpStatus.OK);
	}

	@PostMapping("/events/volunteer")
	public ResponseEntity add_volunteer(@RequestParam("eventId") Integer eventId,
			@RequestBody VolunteerRequestBody request) {
		AddVolunteer volunteer = new AddVolunteer(request.getUserId(), eventId, "registered");
		if (eventId < 0) {
			return new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOG.info("Create Volunteer: " + volunteer.toString());
		return new ResponseEntity<AddVolunteer>(volunteer, HttpStatus.OK);
	}

	@DeleteMapping("/events/volunteer")
	public ResponseEntity<ResultString> remove_volunteer(@RequestParam("eventId") Integer eventId,
			@RequestBody VolunteerRequestBody requestBody) {
		LOG.info("Deleted User Id [{}] for eventId [{}]", requestBody.getUserId(), eventId);
		if (eventId < 0) {
			return new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResultString>(new ResultString("Volunteer withdraw from event"), HttpStatus.OK);
	}

	@PostMapping("/events/feedbacks")
	public ResponseEntity create_feedback(@RequestParam("eventId") Integer eventId,
			@RequestBody FeedbackRequestBody requestBody) {
		Feedback feedback = new Feedback(1, requestBody.getUserId(), eventId, requestBody.getFeedback());
		if (eventId < 0) {
			return new ResponseEntity<ResultString>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOG.info("Create Feedback: " + feedback.toString());
		return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
	}

	@GetMapping("/events/feedbacks")
	public ResponseEntity get_feedback(@RequestParam("eventId") Integer eventId,
			@RequestParam(value = "feedbackId", required = false) Integer feedbackId) {
		ResponseEntity response = null;
		if (feedbackId == null) {
			List<Feedback> feedbacks = new ArrayList<>();
			feedbacks.add(new Feedback(1, 2, eventId, "This event was a blast!"));
			feedbacks.add(new Feedback(2, 3, eventId, "Awesome event!"));
			response = new ResponseEntity(new FeedbackArray(eventId, feedbacks), HttpStatus.OK);
		} else {
			response = new ResponseEntity(new Feedback(feedbackId, 2, eventId, "This event was a blast!"), HttpStatus.OK);
		}
		return response;
	}

}
