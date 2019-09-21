package citi.hackathon.entity;

public class AddVolunteer {
	private Integer userId;
	private Integer eventId;
	private String eventStatus;
	
	public AddVolunteer() {}
	
	public AddVolunteer(Integer userId, Integer eventId, String eventStatus) {
		this.userId = userId;
		this.eventId = eventId;
		this.eventStatus = eventStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Override
	public String toString() {
		return "AddVolunteer [userId=" + userId + ", eventId=" + eventId + ", eventStatus=" + eventStatus + "]";
	}
	
}
