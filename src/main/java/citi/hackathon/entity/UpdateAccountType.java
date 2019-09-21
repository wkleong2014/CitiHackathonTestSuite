package citi.hackathon.entity;

public class UpdateAccountType {
	private String accountType;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public UpdateAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "UpdateAccountType [accountType=" + accountType + "]";
	}
	
}
