package citi.hackathon;

import java.util.Arrays;
import java.util.HashMap;
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
import citi.hackathon.entity.CreateEventRequestBody;
import citi.hackathon.entity.Event;
import citi.hackathon.entity.ResultString;

public class CitiHackathonEventTestSuite {
	private static final Logger LOG = LoggerFactory.getLogger(CitiHackathonEventTestSuite.class);
	private RestTemplate restTemplate = new RestTemplate();
	private static String testUrl;
	private static HttpHeaders headers;

	@BeforeClass
	public static void initialiseTest() {
		testUrl = SpringConfig.getCurrentEventUrl();
		LOG.info("Initialising Test for URL: [{}]", testUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@Test
	public void create_event() {
		String url = testUrl + "/events";
		CreateEventRequestBody requestBody = new CreateEventRequestBody("Dog Shelter Cleaning", "2018-12-28T10:00:00Z",
				"2018-12-28T12:00:00Z", "SPCA", 1, "Dog event", 50, 10, "open");
		HttpEntity<CreateEventRequestBody> entity = new HttpEntity<CreateEventRequestBody>(requestBody, headers);
		try {
			Event result = restTemplate.exchange(url, HttpMethod.POST, entity, Event.class).getBody();
			assertEvent(result);
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
	public void delete_event() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResultString result = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, ResultString.class, params).getBody();
			Assert.assertTrue("<Missing Result String>", result.getResult() != null);
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
	public void delete_invalid_event() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<ResultString> result = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, ResultString.class, params);
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
	
	private void assertEvent(Event result) {
		Assert.assertTrue("<Missing eventId>", result.getEventId() != null);
		Assert.assertTrue("<Missing eventName>", result.getEventName() != null);
		Assert.assertTrue("<Missing startDateTime>", result.getStartDateTime() != null);
		Assert.assertTrue("<Missing endDateTime>", result.getEndDateTime() != null);
		Assert.assertTrue("<Missing organizerName>", result.getOrganizerName() != null);
		Assert.assertTrue("<Missing categoryId>", result.getCategoryId() != null);
		Assert.assertTrue("<Missing description>", result.getDescription() != null);
		Assert.assertTrue("<Missing maxParticipants>", result.getMaxParticipants() != null);
		Assert.assertTrue("<Missing minParticipants>", result.getMinParticipants() != null);
		Assert.assertTrue("<Missing eventStatus>", result.getEventStatus()!= null);
	}

}
