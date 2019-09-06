package citi.hackathon.entity;

public class User {
	
	private int userId;
	private String username;
	private String password;
	private String accountType;
	private String emailAddress;
	private String firstName;
	private String lastName;
	
	
	public User() {
		super();
	}

	public User(int userId, String username, String password, String accountType, String emailAddress, String firstName,
			String lastName) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.accountType = accountType;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
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
}
