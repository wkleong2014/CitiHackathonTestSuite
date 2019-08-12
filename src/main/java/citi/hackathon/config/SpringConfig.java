package citi.hackathon.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {
	
	private static String reportUrl = "http://localhost:8080";
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}

	public static String getReportUrl() {
		return reportUrl;
	}

	public static void setReportUrl(String reportUrl) {
		SpringConfig.reportUrl = reportUrl;
	}
	
	
}
