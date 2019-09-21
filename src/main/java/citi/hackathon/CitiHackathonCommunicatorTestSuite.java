package citi.hackathon;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import citi.hackathon.config.SpringConfig;

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
