package citi.hackathon.entity;

public class Volunteer {
	private Integer userId;
	private String firstName;
	private String lastName;
	private String gender;
	private Integer age;
	private String accountType;

	public Volunteer() {
	}

	public Volunteer(Integer userId, String firstName, String lastName, String gender, Integer age,
			String accountType) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.accountType = accountType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "Volunteer [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
				+ gender + ", age=" + age + ", accountType=" + accountType + "]";
	}

}
