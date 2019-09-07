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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import citi.hackathon.config.SpringConfig;
import citi.hackathon.entity.Account;

public class CitiHackathonAuthenticationTestSuite {
	private static final Logger LOG = LoggerFactory.getLogger(CitiHackathonAuthenticationTestSuite.class);
	private RestTemplate restTemplate = new RestTemplate();
	private static String testUrl;
	private static HttpHeaders headers;

	@BeforeClass
	public static void initialiseTest() {
		testUrl = SpringConfig.getCurrentAuthenticationUrl();
		LOG.info("Initialising Test for URL: [{}]", testUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@Test
	public void create_admin_account() {
		String url = testUrl + "/accounts";
		Account expected = new Account(1, "admin", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "admin", "admin@email.com",
				"Admin", "Johnson");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("username", expected.getUsername());
		params.put("password", expected.getPassword());
		params.put("accountType", expected.getAccountType());
		params.put("emailAddress", expected.getEmailAddress());
		params.put("firstName", expected.getFirstName());
		params.put("lastName", expected.getLastName());
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		Account result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Account.class, params)
				.getBody();
		Assert.assertEquals(expected.getUserId(), result.getUserId());
		Assert.assertEquals(expected.getUsername(), result.getUsername());
		Assert.assertEquals(expected.getPassword(), result.getPassword());
		Assert.assertEquals(expected.getAccountType(), result.getAccountType());
		Assert.assertEquals(expected.getEmailAddress(), result.getEmailAddress());
		Assert.assertEquals(expected.getFirstName(), result.getFirstName());
		Assert.assertEquals(expected.getLastName(), result.getLastName());
	}

	@Test
	public void get_admin_account() {
		String url = testUrl + "/accounts/2";
		Account expected = new Account(2, "admin", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "admin", "admin@email.com",
				"Admin", "Johnson");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Account result = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class).getBody();

		Assert.assertEquals(expected.getUserId(), result.getUserId());
		Assert.assertEquals(expected.getUsername(), result.getUsername());
		Assert.assertEquals(expected.getPassword(), result.getPassword());
		Assert.assertEquals(expected.getAccountType(), result.getAccountType());
		Assert.assertEquals(expected.getEmailAddress(), result.getEmailAddress());
		Assert.assertEquals(expected.getFirstName(), result.getFirstName());
		Assert.assertEquals(expected.getLastName(), result.getLastName());
	}
	
	@Test
	public void get_user_account() {
		String url = testUrl + "/accounts/1";
		Account expected = new Account(1, "my_username", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "user", "user@email.com",
				"Peter", "Johnson");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Account result = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class).getBody();

		Assert.assertEquals(expected.getUserId(), result.getUserId());
		Assert.assertEquals(expected.getUsername(), result.getUsername());
		Assert.assertEquals(expected.getPassword(), result.getPassword());
		Assert.assertEquals(expected.getAccountType(), result.getAccountType());
		Assert.assertEquals(expected.getEmailAddress(), result.getEmailAddress());
		Assert.assertEquals(expected.getFirstName(), result.getFirstName());
		Assert.assertEquals(expected.getLastName(), result.getLastName());
	}

//	
//	@Test
//	public void fail_test() {
//		Assert.assertTrue("You have failed test in this api method /getRequest", false);
//	}
//	@Test
//	public void fail_test_2() {
//		Assert.assertTrue("You have failed test in this api method /postRequest", false);
//	}
//	
//	@Test
//	public void success_test() {
//		Assert.assertEquals(0, 0);
//	}
//	
//	@Test
//	public void sample_test() {
//		HttpEntity<String> entity = new HttpEntity<String>(headers);
//		Sample result = restTemplate.exchange(reportUrl + "/sample", HttpMethod.GET, entity, Sample.class).getBody();
//		Sample expected = new Sample("Sample Username", "Sample Password");
//		Assert.assertEquals(expected.getUsername(), result.getUsername());
//		Assert.assertEquals(expected.getPassword(), result.getPassword());
//	}
//	
//	@Test
//	public void sample_test_with_request_body() {
//		Sample requestBody = new Sample("Sample Username", "Sample Password");
//		HttpEntity<Sample> entity = new HttpEntity<Sample>(requestBody, headers);
//		Sample result = restTemplate.exchange(reportUrl + "/sample_with_request_body", HttpMethod.POST, entity, Sample.class).getBody();
//		Sample expected = new Sample("updated_username", "updated_password");
//		Assert.assertEquals(expected.getUsername(), result.getUsername());
//		Assert.assertEquals(expected.getPassword(), result.getPassword());
//	}
//	
//	@Test
//	public void test_accounts_registration() {
//		SampleAccount result = restTemplate.exchange(reportUrl + "/accounts/registration", HttpMethod.POST, entity, SampleAccount.class).getBody();
//		SampleAccount expected = new SampleAccount(123, "password", "firstName", "lastName", "email", "gender", "birthDate", "nationality", "interest", "region");
//		Assert.assertEquals(expected.getUserId(), result.getUserId());
//		Assert.assertEquals(expected.getPassword(), result.getPassword());
//		Assert.assertEquals(expected.getFirstName(), result.getFirstName());
//		Assert.assertEquals(expected.getLastName(), result.getLastName());
//		Assert.assertEquals(expected.getEmail(), result.getEmail());
//		Assert.assertEquals(expected.getGender(), result.getGender());
//		Assert.assertEquals(expected.getBirthDate(), result.getBirthDate());
//		Assert.assertEquals(expected.getNationality(), result.getNationality());
//		Assert.assertEquals(expected.getInterest(), result.getInterest());
//		Assert.assertEquals(expected.getRegion(), result.getRegion());
//	}

}
