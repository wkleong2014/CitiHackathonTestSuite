package citi.hackathon.entity;

public class ResultString {
	private String result;
	
	public ResultString() {
		
	}
	
	public ResultString(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResultString [result=" + result + "]";
	}
	
	
}
