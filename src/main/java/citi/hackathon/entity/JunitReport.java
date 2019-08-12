package citi.hackathon.entity;

import java.util.List;

import org.junit.runner.notification.Failure;

public class JunitReport {
	
	private int runCount;
	private int failureCount;
	private int successCount;
	private List<Failure> failures;
	
	public JunitReport(int runCount, int failureCount, int successCount, List<Failure> failures) {
		 this.runCount = runCount;
		 this.failureCount = failureCount;
		 this.successCount = successCount;
		 this.failures = failures;
	}

	public int getRunCount() {
		return runCount;
	}

	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public List<Failure> getFailures() {
		return failures;
	}

	public void setFailures(List<Failure> failures) {
		this.failures = failures;
	}
	
	@Override
    public String toString() 
    { 
        return "JunitReport [runCount="+ runCount + ", failureCount="+ failureCount + ", successCount="+ successCount +", failures="+ failures + "]"; 
    } 

}
