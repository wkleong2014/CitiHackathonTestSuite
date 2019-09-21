package citi.hackathon.entity;

import java.util.List;

public class DemographicReport {
	private Integer eventId;
	private String eventName;
	private String startDateTime;
	private String endDateTime;
	private Integer numParticipants;
	private String organizerName;
	private Integer categoryId;
	private List<Volunteer> volunteers;
	private String eventStatus;
	
	public DemographicReport() {}
	
	public DemographicReport(Integer eventId, String eventName, String startDateTime, String endDateTime,
			Integer numParticipants, String organizerName, Integer categoryId, List<Volunteer> volunteers, String eventStatus) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.numParticipants = numParticipants;
		this.organizerName = organizerName;
		this.categoryId = categoryId;
		this.volunteers = volunteers;
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

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<Volunteer> getVolunteers() {
		return volunteers;
	}

	public void setVolunteers(List<Volunteer> volunteers) {
		this.volunteers = volunteers;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Override
	public String toString() {
		return "DemographicReport [eventId=" + eventId + ", eventName=" + eventName + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", numParticipants=" + numParticipants + ", organizerName="
				+ organizerName + ", categoryId=" + categoryId + ", volunteers=" + volunteers + ", eventStatus="
				+ eventStatus + "]";
	}
	
}
