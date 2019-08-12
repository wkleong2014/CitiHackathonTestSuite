package citi.hackathon.restcontroller;

import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import citi.hackathon.CitiHackathonTestSuite;
import citi.hackathon.entity.JunitReport;
import citi.hackathon.entity.Sample;

@RestController
public class TestSuiteController {
	
	@RequestMapping("/testsuite")
	public JunitReport testSuite(@RequestParam(value="url") String url) {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(ParallelComputer.methods(), CitiHackathonTestSuite.class);
		JunitReport report = new JunitReport(result.getRunCount(), result.getIgnoreCount(), result.getFailureCount(), result.getFailures());
		return report;
	}
	
	@RequestMapping("/sample")
	public Sample sample() {
		return new Sample("Sample Username", "Sample Password");
	}
	
	
}
