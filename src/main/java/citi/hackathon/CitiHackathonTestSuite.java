package citi.hackathon;

import java.util.Arrays;

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

import citi.hackathon.config.SpringConfig;
import citi.hackathon.entity.Sample;

public class CitiHackathonTestSuite {
	private static final Logger LOG = LoggerFactory.getLogger(CitiHackathonTestSuite.class);
	private RestTemplate restTemplate = new RestTemplate();
	private static String reportUrl;
	
	@BeforeClass
	public static void initialiseTest() {
		reportUrl = SpringConfig.getReportUrl();
		LOG.info("Initialising Test for URL: [{}]", reportUrl);
	}
	
	@Test
	public void test() {
		Assert.assertTrue(true);
	}
	
	@Test
	public void fail_test() {
		Assert.assertTrue("You have failed test in this api method /getRequest", false);
	}
	@Test
	public void fail_test_2() {
		Assert.assertTrue("You have failed test in this api method /postRequest", false);
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
		
		Sample result = restTemplate.exchange(reportUrl + "/sample", HttpMethod.GET, entity, Sample.class).getBody();
		Assert.assertEquals(new Sample("Sample Username", "Sample Password").getUsername(), result.getUsername());
	}
}
