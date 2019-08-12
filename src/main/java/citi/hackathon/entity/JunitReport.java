package citi.hackathon.entity;

import java.util.List;

import org.junit.runner.notification.Failure;

public class JunitReport {
	
	private int runCount;
	private int ignoreCount;
	private int failureCount;
	private List<Failure> failures;
	
	public JunitReport(int runCount, int ignoreCount, int failureCount, List<Failure> failures) {
		 this.runCount = runCount;
		 this.ignoreCount = ignoreCount;
		 this.failureCount = failureCount;
		 this.failures = failures;
	}

	public int getRunCount() {
		return runCount;
	}

	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}

	public int getIgnoreCount() {
		return ignoreCount;
	}

	public void setIgnoreCount(int ignoreCount) {
		this.ignoreCount = ignoreCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public List<Failure> getFailures() {
		return failures;
	}

	public void setFailures(List<Failure> failures) {
		this.failures = failures;
	}
	
	

}
