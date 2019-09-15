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
import citi.hackathon.entity.Account;
import citi.hackathon.entity.ResultString;
import citi.hackathon.entity.UpdateAccount;

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
	public void get_admin_account() {
		String url = testUrl + "/accounts";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Account result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Account.class, params).getBody();
			assertAccount(result);
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
	public void get_user_account() {
		String url = testUrl + "/accounts";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "2");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Account result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Account.class, params).getBody();
			assertAccount(result);
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
	public void create_admin_account() {
		String url = testUrl + "/accounts";
		Account expected = new Account(1, "admin", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "admin", "admin_nimda@email.com",
				"Admin", "Nimda", true);
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
		try {
			Account result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.POST, entity, Account.class, params).getBody();
			assertAccount(result);
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
	public void create_user_account() {
		String url = testUrl + "/accounts";
		Account expected = new Account(2, "peter", "47b7bfb65fa83ac9a71dcb0f6296bb6e", "user",
				"peter_johnson@email.com", "Peter", "Johnson", false);
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
		try {
			Account result = restTemplate
					.exchange(builder.toUriString(), HttpMethod.POST, entity, Account.class, params).getBody();
			assertAccount(result);
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
	public void update_admin_account() {
		String url = testUrl + "/accounts";
		UpdateAccount requestBody = new UpdateAccount("new_password", "new_email", "new_firstname", "new_lastname");
		HttpEntity<UpdateAccount> entity = new HttpEntity<UpdateAccount>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Account result = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, Account.class, params).getBody();
			assertAccount(result);
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
	public void update_user_account() {
		String url = testUrl + "/accounts";
		UpdateAccount requestBody = new UpdateAccount("new_password", "new_email", "new_firstname", "new_lastname");
		HttpEntity<UpdateAccount> entity = new HttpEntity<UpdateAccount>(requestBody, headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "2");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Account result = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, entity, Account.class, params).getBody();
			assertAccount(result);
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
	public void delete_account() {
		String url = testUrl + "/accounts";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			Account result = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, Account.class, params).getBody();
			assertAccount(result);
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
	public void reset_account_password() {
		String url = testUrl + "/accounts";
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1");
		params.put("email", "peter_johnson@email.com");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		try {
			ResultString result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, ResultString.class, params).getBody();
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

	private void assertAccount(Account result) {
		Assert.assertTrue("<Missing userId>", result.getUserId() != null);
		Assert.assertTrue("<Missing username>", result.getUsername() != null);
		Assert.assertTrue("<Missing password>", result.getPassword() != null);
		Assert.assertTrue("<Missing accountType>", result.getAccountType() != null);
		Assert.assertTrue("<Missing emailAddress>", result.getEmailAddress() != null);
		Assert.assertTrue("<Missing firstName>", result.getFirstName() != null);
		Assert.assertTrue("<Missing lastName>", result.getLastName() != null);
		Assert.assertTrue("<Missing isAdmin>", result.getIsAdmin() != null);
	}

}
