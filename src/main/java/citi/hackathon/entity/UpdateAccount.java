package citi.hackathon.entity;

public class UpdateAccount {

	private String password;
	private String emailAddress;
	private String firstName;
	private String lastName;

	// Default Empty Constructor
	public UpdateAccount() {
	}

	// Parameterise Constructor
	public UpdateAccount(String password, String emailAddress, String firstName, String lastName) {
		this.password = password;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "UpdateAccount [password=" + password + ", emailAddress=" + emailAddress + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}

}
