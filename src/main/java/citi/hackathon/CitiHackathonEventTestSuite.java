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
import citi.hackathon.entity.AddVolunteer;
import citi.hackathon.entity.AllEvents;
import citi.hackathon.entity.Category;
import citi.hackathon.entity.CategoryArray;
import citi.hackathon.entity.CategoryRequestBody;
import citi.hackathon.entity.Event;
import citi.hackathon.entity.EventRequestBody;
import citi.hackathon.entity.Feedback;
import citi.hackathon.entity.FeedbackArray;
import citi.hackathon.entity.FeedbackRequestBody;
import citi.hackathon.entity.ResultString;
import citi.hackathon.entity.VolunteerRequestBody;

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
		EventRequestBody requestBody = new EventRequestBody("Dog Shelter Cleaning", "2018-12-28T10:00:00Z",
				"2018-12-28T12:00:00Z", "SPCA", 1, "Dog event", 50, 10, "open");
		HttpEntity<EventRequestBody> entity = new HttpEntity<EventRequestBody>(requestBody, headers);
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
			ResponseEntity<ResultString> result = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE,
					entity, ResultString.class, params);
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
	public void get_event() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Event result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Event.class, params)
					.getBody();
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
	public void get_invalid_event() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<ResultString> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
					ResultString.class, params);
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
	public void get_all_event() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		try {
			AllEvents result = restTemplate.exchange(url, HttpMethod.GET, entity, AllEvents.class).getBody();
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events is not empty>", result.getEvents().size() > 0);
			List<Event> events = result.getEvents();
			for (Event event : events) {
				assertEvent(event);
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
	public void get_event_by_search() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("search", "Dog");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			AllEvents result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, entity, AllEvents.class, params).getBody();
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events is not empty>", result.getEvents().size() > 0);
			List<Event> events = result.getEvents();
			for (Event event : events) {
				assertEvent(event);
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
	public void get_event_by_time() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("from_time", "2018-12-27");
		params.put("to_time", "2019-01-01");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			AllEvents result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, entity, AllEvents.class, params).getBody();
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events is not empty>", result.getEvents().size() > 0);
			List<Event> events = result.getEvents();
			for (Event event : events) {
				assertEvent(event);
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
	public void get_event_by_category_id() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("categoryId", "1");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			AllEvents result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, entity, AllEvents.class, params).getBody();
			Assert.assertTrue("<Missing events>", result.getEvents() != null);
			Assert.assertTrue("<Events is not empty>", result.getEvents().size() > 0);
			List<Event> events = result.getEvents();
			for (Event event : events) {
				assertEvent(event);
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
	public void get_event_with_invalid_request_param() {
		String url = testUrl + "/events";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("categoryId", "1");
		params.put("search", "Dog");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
					AllEvents.class, params);
			Assert.assertEquals(400, result.getStatusCodeValue());
		} catch (HttpClientErrorException e) {
			switch (e.getRawStatusCode()) {
			case 404:
				Assert.assertTrue("Invalid Endpoint", false);
				break;
			case 400:
				Assert.assertEquals("Expecting Internal Server Error 400", 400, e.getRawStatusCode());
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
	public void update_event() {
		String url = testUrl + "/events";
		EventRequestBody requestBody = new EventRequestBody("Dog Shelter Cleaning", "2018-12-28T10:00:00Z",
				"2018-12-28T12:00:00Z", "SPCA", 1, "Dog event", 50, 10, "open");
		HttpEntity<EventRequestBody> entity = new HttpEntity<EventRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Event result = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, Event.class, params)
					.getBody();
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
	public void create_category() {
		String url = testUrl + "/categories";
		CategoryRequestBody requestBody = new CategoryRequestBody("Animals");
		HttpEntity<CategoryRequestBody> entity = new HttpEntity<CategoryRequestBody>(requestBody, headers);
		try {
			Category result = restTemplate.exchange(url, HttpMethod.POST, entity, Category.class).getBody();
			assertCategory(result);
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
	public void get_category() {
		String url = testUrl + "/categories";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		try {
			CategoryArray result = restTemplate.exchange(url, HttpMethod.GET, entity, CategoryArray.class).getBody();
			Assert.assertTrue("<Missing categories>", result.getCategories() != null);
			Assert.assertTrue("<Categories is not empty>", result.getCategories().size() > 0);
			List<Category> categories = result.getCategories();
			for (Category category : categories) {
				assertCategory(category);
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
	public void update_category() {
		String url = testUrl + "/categories";
		CategoryRequestBody requestBody = new CategoryRequestBody("Animals");
		HttpEntity<CategoryRequestBody> entity = new HttpEntity<CategoryRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("categoryId", "1");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Category result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.PUT, entity, Category.class, params).getBody();
			assertCategory(result);
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
	public void update_invalid_category() {
		String url = testUrl + "/categories";
		CategoryRequestBody requestBody = new CategoryRequestBody("Animals");
		HttpEntity<CategoryRequestBody> entity = new HttpEntity<CategoryRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("categoryId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<Event> result = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity,
					Event.class, params);
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
	public void delete_category() {
		String url = testUrl + "/categories";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("categoryId", "1");
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
	public void delete_invalid_category() {
		String url = testUrl + "/categories";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("categoryId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<ResultString> result = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE,
					entity, ResultString.class, params);
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
	public void add_volunteer_to_event() {
		String url = testUrl + "/events/volunteer";
		VolunteerRequestBody requestBody = new VolunteerRequestBody(2);
		HttpEntity<VolunteerRequestBody> entity = new HttpEntity<VolunteerRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			AddVolunteer result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.POST, entity, AddVolunteer.class, params).getBody();
			Assert.assertTrue("<Missing userId>", result.getUserId() != null);
			Assert.assertTrue("<Missing eventId>", result.getEventId() != null);
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
	public void add_invalid_volunteer_to_event() {
		String url = testUrl + "/events/volunteer";
		VolunteerRequestBody requestBody = new VolunteerRequestBody(2);
		HttpEntity<VolunteerRequestBody> entity = new HttpEntity<VolunteerRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<AddVolunteer> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					AddVolunteer.class, params);
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
	public void remove_volunteer() {
		String url = testUrl + "/events/volunteer";
		VolunteerRequestBody requestBody = new VolunteerRequestBody(2);
		HttpEntity<VolunteerRequestBody> entity = new HttpEntity<VolunteerRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
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
	public void remove_invalid_volunteer() {
		String url = testUrl + "/events/volunteer";
		VolunteerRequestBody requestBody = new VolunteerRequestBody(2);
		HttpEntity<VolunteerRequestBody> entity = new HttpEntity<VolunteerRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<ResultString> result = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE,
					entity, ResultString.class, params);
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
	public void create_feedback() {
		String url = testUrl + "/events/feedbacks";
		FeedbackRequestBody requestBody = new FeedbackRequestBody(2, "The event was a blast");
		HttpEntity<FeedbackRequestBody> entity = new HttpEntity<FeedbackRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Feedback result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.POST, entity, Feedback.class, params).getBody();
			Assert.assertTrue("<Missing feedbackId>", result.getFeedbackId() != null);
			Assert.assertTrue("<Missing userId>", result.getUserId() != null);
			Assert.assertTrue("<Missing eventId>", result.getEventId() != null);
			Assert.assertTrue("<Missing feedback>", result.getFeedback() != null);
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
	public void create_feedback_for_invalid_eventid() {
		String url = testUrl + "/events/feedbacks";
		FeedbackRequestBody requestBody = new FeedbackRequestBody(2, "The event was a blast");
		HttpEntity<FeedbackRequestBody> entity = new HttpEntity<FeedbackRequestBody>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "-100");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResponseEntity<Feedback> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					Feedback.class, params);
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
	public void get_all_feedbacks() {
		String url = testUrl + "/events/feedbacks";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			FeedbackArray result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, entity, FeedbackArray.class, params).getBody();
			Assert.assertTrue("<Missing eventId>", result.getEventId() != null);
			Assert.assertTrue("<Missing feedbacks>", result.getFeedbacks() != null);
			Assert.assertTrue("<feedbacks is not empty>", result.getFeedbacks().size() > 0);
			List<Feedback> feedbacks = result.getFeedbacks();
			for (Feedback feedback : feedbacks) {
				Assert.assertTrue("<Missing feedbackId>", feedback.getFeedbackId() != null);
				Assert.assertTrue("<Missing userId>", feedback.getUserId() != null);
				Assert.assertTrue("<Missing eventId>", feedback.getEventId() != null);
				Assert.assertTrue("<Missing feedback>", feedback.getFeedback() != null);
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
	public void get_a_feedback() {
		String url = testUrl + "/events/feedbacks";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("eventId", "1002");
		params.put("feedbackId", "1");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Feedback result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, entity, Feedback.class, params).getBody();
			Assert.assertTrue("<Missing feedbackId>", result.getFeedbackId() != null);
			Assert.assertTrue("<Missing userId>", result.getUserId() != null);
			Assert.assertTrue("<Missing eventId>", result.getEventId() != null);
			Assert.assertTrue("<Missing feedback>", result.getFeedback() != null);
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
		Assert.assertTrue("<Missing eventStatus>", result.getEventStatus() != null);
	}

	private void assertCategory(Category result) {
		Assert.assertTrue("<Missing categoryId>", result.getCategoryId() != null);
		Assert.assertTrue("<Missing eventcategory>", result.getCategory() != null);
	}

}
