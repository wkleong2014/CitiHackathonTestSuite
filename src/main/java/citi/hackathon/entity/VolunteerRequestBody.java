package citi.hackathon.entity;

public class VolunteerRequestBody {
	private Integer userId;
	
	public VolunteerRequestBody() {}

	public VolunteerRequestBody(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "VolunteerRequestBody [userId=" + userId + "]";
	}
	
}
