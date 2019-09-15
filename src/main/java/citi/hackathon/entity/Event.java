package citi.hackathon.entity;

public class Event {
	private Integer eventId;
	private String eventName;
	private String startDateTime;
	private String endDateTime;
	private Integer numParticipants;
	private String categoryId;
	private String eventStatus;
	
	public Event() {}

	public Event(Integer eventId, String eventName, String startDateTime, String endDateTime, Integer numParticipants,
			String categoryId, String eventStatus) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.numParticipants = numParticipants;
		this.categoryId = categoryId;
		this.eventStatus = eventStatus;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getNumParticipants() {
		return numParticipants;
	}

	public void setNumParticipants(Integer numParticipants) {
		this.numParticipants = numParticipants;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventName=" + eventName + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", numParticipants=" + numParticipants + ", categoryId=" + categoryId
				+ ", eventStatus=" + eventStatus + "]";
	}
}
