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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import citi.hackathon.config.SpringConfig;
import citi.hackathon.entity.DemographicReport;
import citi.hackathon.entity.Event;
import citi.hackathon.entity.OrganizationBreakdownReport;
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
			List<Volunteer> volunteers = result.getVolunteers();
			for (Volunteer volunteer: volunteers) {
				Assert.assertTrue("<Missing userId>", volunteer.getUserId() != null);
				Assert.assertTrue("<Missing firstName>", volunteer.getFirstName() != null);
				Assert.assertTrue("<Missing lastName>", volunteer.getLastName() != null);
				Assert.assertTrue("<Missing gender>", volunteer.getGender() != null);
				Assert.assertTrue("<Missing age>", volunteer.getAge() != null);
				Assert.assertTrue("<Missing accountType>", volunteer.getAccountType() != null);
				
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
	public void get_organization_breakdown_report() {
		String url = testUrl + "/reports/organization-breakdown";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("organizerName", "SPCA");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			OrganizationBreakdownReport result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, OrganizationBreakdownReport.class, params).getBody();
			List<Event> events = result.getEvents();
			for (Event event: events) {
				Assert.assertTrue("<Missing eventId>", event.getEventId() != null);
				Assert.assertTrue("<Missing eventName>", event.getEventName() != null);
				Assert.assertTrue("<Missing startDateTime>", event.getStartDateTime() != null);
				Assert.assertTrue("<Missing endDateTime>", event.getEndDateTime() != null);
				Assert.assertTrue("<Missing numParticipants>", event.getNumParticipants() != null);
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
	
}
