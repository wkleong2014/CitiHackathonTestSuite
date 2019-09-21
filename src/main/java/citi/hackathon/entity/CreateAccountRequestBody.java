package citi.hackathon.entity;

public class CreateAccountRequestBody {
	
	private String username;
	private String password;
	private String accountType;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String gender;
	private Integer age;
	
	public CreateAccountRequestBody() {}

	public CreateAccountRequestBody(String username, String password, String accountType, String emailAddress,
			String firstName, String lastName, String gender, Integer age) {
		this.username = username;
		this.password = password;
		this.accountType = accountType;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "CreateAccountRequestBody [username=" + username + ", password=" + password + ", accountType="
				+ accountType + ", emailAddress=" + emailAddress + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", age=" + age + "]";
	}
	
}
