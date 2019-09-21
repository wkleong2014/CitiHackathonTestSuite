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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import citi.hackathon.config.SpringConfig;
import citi.hackathon.entity.ResultString;

public class CitiHackathonCommunicatorTestSuite {
	private static final Logger LOG = LoggerFactory.getLogger(CitiHackathonCommunicatorTestSuite.class);
	private RestTemplate restTemplate = new RestTemplate();
	private static String testUrl;
	private static HttpHeaders headers;
	
	@BeforeClass
	public static void initialiseTest() {
		testUrl = SpringConfig.getCurrentCommunicatorUrl();
		LOG.info("Initialising Test for URL: [{}]", testUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void create_full_subscription() {
		String url = testUrl + "/events/fullSubscription";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		params.put("userId", "2");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResultString result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.POST, entity, ResultString.class, params).getBody();
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
	public void delete_full_subscription() {
		String url = testUrl + "/events/fullSubscription";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		params.put("userId", "2");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResultString result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.DELETE, entity, ResultString.class, params).getBody();
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
	public void create_event_update() {
		String url = testUrl + "/events/eventUpdates";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		params.put("userId", "2");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResultString result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.POST, entity, ResultString.class, params).getBody();
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
	public void create_upcoming_event_subscription() {
		String url = testUrl + "/events/upcomingEventSubscription";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		params.put("userId", "2");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResultString result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.POST, entity, ResultString.class, params).getBody();
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
	public void delete_upcoming_event_subscription() {
		String url = testUrl + "/events/upcomingEventSubscription";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		params.put("userId", "2");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResultString result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.DELETE, entity, ResultString.class, params).getBody();
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
	public void test() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			LOG.error("Thread sleep failed: ", e);
		}
		Assert.assertTrue(true);
		LOG.info("Completed Test in Communicator for reportUrl: " + testUrl);
	}
	
}
