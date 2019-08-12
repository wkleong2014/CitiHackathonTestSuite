package citi.hackathon.restcontroller;

import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import citi.hackathon.CitiHackathonTestSuite;
import citi.hackathon.config.SpringConfig;
import citi.hackathon.entity.JunitReport;
import citi.hackathon.entity.Sample;

@RestController
public class TestSuiteController {
	private static final Logger LOG = LoggerFactory.getLogger(TestSuiteController.class);
	
	@RequestMapping("/testsuite")
	public JunitReport testSuite(@RequestParam(value="url") String url) {
		JUnitCore junit = new JUnitCore();
		SpringConfig.setReportUrl(url);
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getFailureCount(), result.getRunCount() - result.getFailureCount() - result.getIgnoreCount(), result.getFailures());
		LOG.info(report.toString());
		return report;
	}
	
	@RequestMapping("/sample")
	public Sample sample() {
		return new Sample("Sample Username", "Sample Password");
	}
	
	
}
