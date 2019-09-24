package citi.hackathon.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {
	
	private static String currentAuthenticationUrl;
	private static String currentEventUrl;
	private static String currentCommunicatorUrl;
	private static String currentReportUrl;
	private static AtomicInteger authenticationCounter = new AtomicInteger();
	private static AtomicInteger eventCounter = new AtomicInteger();
	private static AtomicInteger communicationCounter = new AtomicInteger();
	private static AtomicInteger reportCounter = new AtomicInteger();
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}

	public static synchronized String getCurrentAuthenticationUrl() {
		return currentAuthenticationUrl;
	}

	public static synchronized void setCurrentAuthenticationUrl(String currentAuthenticationUrl) {
		SpringConfig.currentAuthenticationUrl = currentAuthenticationUrl;
	}

	public static synchronized String getCurrentEventUrl() {
		return currentEventUrl;
	}

	public static synchronized void setCurrentEventUrl(String currentEventUrl) {
		SpringConfig.currentEventUrl = currentEventUrl;
	}

	public static synchronized String getCurrentCommunicatorUrl() {
		return currentCommunicatorUrl;
	}

	public static synchronized void setCurrentCommunicatorUrl(String currentCommunicatorUrl) {
		SpringConfig.currentCommunicatorUrl = currentCommunicatorUrl;
	}

	public static synchronized String getCurrentReportUrl() {
		return currentReportUrl;
	}

	public static synchronized void setCurrentReportUrl(String currentReportUrl) {
		SpringConfig.currentReportUrl = currentReportUrl;
	}

	public static int getAuthenticationCount() {
		return authenticationCounter.get();
	}

	public static int incrementAuthenticationCount() {
		return authenticationCounter.incrementAndGet();
	}
	
	public static int getEventCounter() {
		return eventCounter.get();
	}

	public static int incrementEventCount() {
		return eventCounter.incrementAndGet();
	}
	
	public static int getReportCount() {
		return reportCounter.get();
	}

	public static int incrementReportCount() {
		return reportCounter.incrementAndGet();
	}
	
	public static int getCommunicationCount() {
		return communicationCounter.get();
	}

	public static int incrementCommunicationCount() {
		return communicationCounter.incrementAndGet();
	}
	
	
	
}
