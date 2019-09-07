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
import citi.hackathon.entity.SampleAccount;
import citi.hackathon.entity.Sample;

public class CitiHackathonReportTestSuite {
	private static final Logger LOG = LoggerFactory.getLogger(CitiHackathonReportTestSuite.class);
	private RestTemplate restTemplate = new RestTemplate();
	private static String reportUrl;
	private static HttpHeaders headers;
	
	@BeforeClass
	public static void initialiseTest() {
		reportUrl = SpringConfig.getCurrentReportUrl();
		LOG.info("Initialising Test for URL: [{}]", reportUrl);
		
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
		LOG.info("Completed Test in Report for reportUrl: " + reportUrl);
	}
	
}
