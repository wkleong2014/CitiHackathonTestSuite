package citi.hackathon.entity;

public class ResetPassword {
	private String username;
	private String emailAddress;
	
	public ResetPassword(String username, String emailAddress) {
		this.username = username;
		this.emailAddress = emailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "ResetPassword [username=" + username + ", emailAddress=" + emailAddress + "]";
	}
	
	
}
