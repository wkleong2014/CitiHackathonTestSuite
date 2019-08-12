package citi.hackathon;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import citi.hackathon.entity.Sample;

public class CitiHackathonTestSuite {
	private RestTemplate restTemplate = new RestTemplate();
	
	@Test
	public void test() {
		System.out.println("running test");
	}
	
	@Test
	public void fail_test() {
		Assert.assertTrue("You have failed test in this api method /getRequest", false);
	}
	
	@Test
	public void success_test() {
		Assert.assertEquals(0, 0);
	}
	
	@Test
	public void sample_test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		
		Sample result = restTemplate.exchange("http://localhost:8080/sample", HttpMethod.GET, entity, Sample.class).getBody();
		Assert.assertEquals(new Sample("Sample Username", "Sample Password").getUsername(), result.getUsername());
	}
}
