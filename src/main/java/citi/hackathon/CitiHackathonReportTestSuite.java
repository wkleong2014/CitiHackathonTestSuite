package citi.hackathon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import citi.hackathon.config.SpringConfig;
import citi.hackathon.entity.Account;
import citi.hackathon.entity.DemographicReport;
import citi.hackathon.entity.ReportEvent;
import citi.hackathon.entity.UserHistoricalBreakdownReport;
import citi.hackathon.entity.BreakdownReport;
import citi.hackathon.entity.Volunteer;

public class CitiHackathonReportTestSuite {
	private static final Logger LOG = LoggerFactory.getLogger(CitiHackathonReportTestSuite.class);
	private RestTemplate restTemplate = new RestTemplate();
	private static String testUrl;
	private static HttpHeaders headers;
	
	@BeforeClass
	public static void initialiseTest() {
		testUrl = SpringConfig.getCurrentReportUrl();
		LOG.info("Initialising Test for URL: [{}]", testUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void get_demographic_report() {
		String url = testUrl + "/reports/demographic";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			DemographicReport result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, DemographicReport.class, params).getBody();
			Assert.assertTrue("<Missing eventId>", result.getEventId() != null);
			Assert.assertTrue("<Missing eventName>", result.getEventName() != null);
			Assert.assertTrue("<Missing startDateTime>", result.getStartDateTime() != null);
			Assert.assertTrue("<Missing endDateTime>", result.getEndDateTime() != null);
			Assert.assertTrue("<Missing numParticipants>", result.getNumParticipants() != null);
			Assert.assertTrue("<Missing organizerName>", result.getOrganizerName() != null);
			Assert.assertTrue("<Missing categoryId>", result.getCategoryId() != null);
			Assert.assertTrue("<Missing volunteers>", result.getVolunteers() != null);
			Assert.assertTrue("<Volunteers should not be empty>", result.getVolunteers().size() > 0);
			List<Volunteer> volunteers = result.getVolunteers();
			for (Volunteer volunteer: volunteers) {
				Assert.assertTrue("<Missing userId>", volunteer.getUserId() != null);
				Assert.assertTrue("<Missing firstName>", volunteer.getFirstName() != null);
				Assert.assertTrue("<Missing lastName>", volunteer.getLastName() != null);
				Assert.assertTrue("<Missing gender>", volunteer.getGender() != null);
				Assert.assertTrue("<Missing age>", volunteer.getAge() != null);
				Assert.assertTrue("<Missing accountType>", volunteer.getAccountType() != null);
				
			}
			Assert.assertTrue("<Missing eventStatus>", result.getEventStatus() != null);
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_invalid_demographic_report() {
		String url = testUrl + "/reports/demographic";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<DemographicReport> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, DemographicReport.class, params);
			Assert.assertEquals(500, result.getStatusCodeValue());
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (HttpServerErrorException e) {
			Assert.assertEquals("Expecting Internal Server Error 500", 500, e.getRawStatusCode());
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_organization_breakdown_report() {
		String url = testUrl + "/reports/organization";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("organizerName", "SPCA");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			BreakdownReport result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, BreakdownReport.class, params).getBody();
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			List<ReportEvent> events = result.getEvents();
			for (ReportEvent event: events) {
				Assert.assertTrue("<Missing eventId>", event.getEventId() != null);
				Assert.assertTrue("<Missing eventName>", event.getEventName() != null);
				Assert.assertTrue("<Missing startDateTime>", event.getStartDateTime() != null);
				Assert.assertTrue("<Missing endDateTime>", event.getEndDateTime() != null);
				Assert.assertTrue("<Missing numParticipants>", event.getNumParticipants() != null);
				Assert.assertTrue("<Missing volunteers>", event.getVolunteers() != null);
				Assert.assertTrue("<Volunteers should not be empty>", event.getVolunteers().size() > 0);
				List<Volunteer> volunteers = event.getVolunteers();
				for (Volunteer volunteer: volunteers) {
					Assert.assertTrue("<Missing userId>", volunteer.getUserId() != null);
					Assert.assertTrue("<Missing firstName>", volunteer.getFirstName() != null);
					Assert.assertTrue("<Missing lastName>", volunteer.getLastName() != null);
					Assert.assertTrue("<Missing gender>", volunteer.getGender() != null);
					Assert.assertTrue("<Missing age>", volunteer.getAge() != null);
					Assert.assertTrue("<Missing accountType>", volunteer.getAccountType() != null);
					
				}
				Assert.assertTrue("<Missing categoryId>", event.getCategoryId() != null);
				Assert.assertTrue("<Missing eventStatus>", event.getEventStatus() != null);
			}
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_invalid_organization_report() {
		String url = testUrl + "/reports/organization";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("organizerName", "SPCC");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<BreakdownReport> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, BreakdownReport.class, params);
			Assert.assertEquals(500, result.getStatusCodeValue());
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (HttpServerErrorException e) {
			Assert.assertEquals("Expecting Internal Server Error 500", 500, e.getRawStatusCode());
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_all_historical_report_for_admin() {
		String url = testUrl + "/reports/historical";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		try {
			BreakdownReport result = restTemplate.exchange(url, HttpMethod.GET, entity, BreakdownReport.class).getBody();
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			List<ReportEvent> events = result.getEvents();
			for (ReportEvent event: events) {
				Assert.assertTrue("<Missing eventId>", event.getEventId() != null);
				Assert.assertTrue("<Missing eventName>", event.getEventName() != null);
				Assert.assertTrue("<Missing startDateTime>", event.getStartDateTime() != null);
				Assert.assertTrue("<Missing endDateTime>", event.getEndDateTime() != null);
				Assert.assertTrue("<Missing numParticipants>", event.getNumParticipants() != null);
				Assert.assertTrue("<Missing volunteers>", event.getVolunteers() != null);
				Assert.assertTrue("<Volunteers should not be empty>", event.getVolunteers().size() > 0);
				List<Volunteer> volunteers = event.getVolunteers();
				for (Volunteer volunteer: volunteers) {
					Assert.assertTrue("<Missing userId>", volunteer.getUserId() != null);
					Assert.assertTrue("<Missing firstName>", volunteer.getFirstName() != null);
					Assert.assertTrue("<Missing lastName>", volunteer.getLastName() != null);
					Assert.assertTrue("<Missing gender>", volunteer.getGender() != null);
					Assert.assertTrue("<Missing age>", volunteer.getAge() != null);
					Assert.assertTrue("<Missing accountType>", volunteer.getAccountType() != null);
				}
				Assert.assertTrue("<Missing categoryId>", event.getCategoryId() != null);
				Assert.assertTrue("<Missing eventStatus>", event.getEventStatus() != null);
				Assert.assertTrue("<Missing organizerName>", event.getOrganizerName() != null);
			}
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_from_to_historical_report_for_admin() {
		String url = testUrl + "/reports/historical";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("fromDate", "2018-12-25");
		params.put("toDate", "2019-06-07");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			BreakdownReport result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, BreakdownReport.class, params).getBody();
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events should not be empty>", result.getEvents().size() > 0);
			List<ReportEvent> events = result.getEvents();
			for (ReportEvent event: events) {
				Assert.assertTrue("<Missing eventId>", event.getEventId() != null);
				Assert.assertTrue("<Missing eventName>", event.getEventName() != null);
				Assert.assertTrue("<Missing startDateTime>", event.getStartDateTime() != null);
				Assert.assertTrue("<Missing endDateTime>", event.getEndDateTime() != null);
				Assert.assertTrue("<Missing numParticipants>", event.getNumParticipants() != null);
				Assert.assertTrue("<Missing volunteers>", event.getVolunteers() != null);
				Assert.assertTrue("<Volunteers should not be empty>", event.getVolunteers().size() > 0);
				List<Volunteer> volunteers = event.getVolunteers();
				for (Volunteer volunteer: volunteers) {
					Assert.assertTrue("<Missing userId>", volunteer.getUserId() != null);
					Assert.assertTrue("<Missing firstName>", volunteer.getFirstName() != null);
					Assert.assertTrue("<Missing lastName>", volunteer.getLastName() != null);
					Assert.assertTrue("<Missing gender>", volunteer.getGender() != null);
					Assert.assertTrue("<Missing age>", volunteer.getAge() != null);
					Assert.assertTrue("<Missing accountType>", volunteer.getAccountType() != null);
				}
				Assert.assertTrue("<Missing categoryId>", event.getCategoryId() != null);
				Assert.assertTrue("<Missing eventStatus>", event.getEventStatus() != null);
				Assert.assertTrue("<Missing organizerName>", event.getOrganizerName() != null);
			}
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_from_to_historical_report_for_user() {
		String url = testUrl + "/reports/user-historical";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1");
		params.put("fromDate", "2018-12-25");
		params.put("toDate", "2019-06-07");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			UserHistoricalBreakdownReport result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, UserHistoricalBreakdownReport.class, params).getBody();
			Assert.assertTrue("<Missing numEvents>", result.getNumEvents() != null);
			Assert.assertTrue("<Missing totalHours>", result.getTotalHours() != null);
			Assert.assertTrue("<Missing fromDate>", result.getFromDate() != null);
			Assert.assertTrue("<Missing toDate>", result.getToDate() != null);
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events should not be empty>", result.getEvents().size() > 0);
			List<ReportEvent> events = result.getEvents();
			for (ReportEvent event: events) {
				Assert.assertTrue("<Missing eventId>", event.getEventId() != null);
				Assert.assertTrue("<Missing eventName>", event.getEventName() != null);
				Assert.assertTrue("<Missing startDateTime>", event.getStartDateTime() != null);
				Assert.assertTrue("<Missing endDateTime>", event.getEndDateTime() != null);
				Assert.assertTrue("<Missing numHours>", event.getNumHours() != null);
				Assert.assertTrue("<Missing categoryId>", event.getCategoryId() != null);
				Assert.assertTrue("<Missing eventStatus>", event.getEventStatus() != null);
				Assert.assertTrue("<Missing organizerName>", event.getOrganizerName() != null);
			}
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_empty_historical_report_for_user() {
		String url = testUrl + "/reports/user-historical";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		try {
			UserHistoricalBreakdownReport result = restTemplate.exchange(url, HttpMethod.GET, entity, UserHistoricalBreakdownReport.class).getBody();
			Assert.assertTrue("<Missing numEvents>", result.getNumEvents() != null);
			Assert.assertTrue("<Missing totalHours>", result.getTotalHours() != null);
			Assert.assertTrue("<Missing fromDate>", result.getFromDate() != null);
			Assert.assertTrue("<Missing toDate>", result.getToDate() != null);
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events should be empty>", result.getEvents().size() == 0);
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	@Test
	public void get_all_historical_report_for_user() {
		String url = testUrl + "/reports/user-historical";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			UserHistoricalBreakdownReport result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, UserHistoricalBreakdownReport.class, params).getBody();
			Assert.assertTrue("<Missing numEvents>", result.getNumEvents() != null);
			Assert.assertTrue("<Missing totalHours>", result.getTotalHours() != null);
			Assert.assertTrue("<Missing fromDate>", result.getFromDate() != null);
			Assert.assertTrue("<Missing toDate>", result.getToDate() != null);
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events should not be empty>", result.getEvents().size() > 0);
			List<ReportEvent> events = result.getEvents();
			for (ReportEvent event: events) {
				Assert.assertTrue("<Missing eventId>", event.getEventId() != null);
				Assert.assertTrue("<Missing eventName>", event.getEventName() != null);
				Assert.assertTrue("<Missing startDateTime>", event.getStartDateTime() != null);
				Assert.assertTrue("<Missing endDateTime>", event.getEndDateTime() != null);
				Assert.assertTrue("<Missing numHours>", event.getNumHours() != null);
				Assert.assertTrue("<Missing categoryId>", event.getCategoryId() != null);
				Assert.assertTrue("<Missing eventStatus>", event.getEventStatus() != null);
				Assert.assertTrue("<Missing organizerName>", event.getOrganizerName() != null);
			}
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			default:
				Assert.assertTrue("Test Fail due to error " + e.getStatusCode(), false);
				break;
			}
		} catch (Exception e) {
			Assert.assertTrue("Test Fail due to exception: " + e, false);
		}
	}
	
	
}
