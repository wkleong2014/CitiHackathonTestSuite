package citi.hackathon.entity;

import java.util.List;

public class ReportEvent {
	private Integer eventId;
	private String eventName;
	private String startDateTime;
	private String endDateTime;
	private Integer numParticipants;
	private List<Volunteer> volunteers;
	private Integer categoryId;
	private String eventStatus;
	private String organizerName;
	private Integer numHours;

	public ReportEvent() {
	}

	public ReportEvent(Integer eventId, String eventName, String startDateTime, String endDateTime,
			Integer numParticipants, List<Volunteer> volunteers, Integer categoryId, String eventStatus) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.numParticipants = numParticipants;
		this.volunteers = volunteers;
		this.categoryId = categoryId;
		this.eventStatus = eventStatus;
	}

	public ReportEvent(Integer eventId, String eventName, String startDateTime, String endDateTime,
			Integer numParticipants, List<Volunteer> volunteers, Integer categoryId, String eventStatus,
			String organizerName) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.numParticipants = numParticipants;
		this.volunteers = volunteers;
		this.categoryId = categoryId;
		this.eventStatus = eventStatus;
		this.organizerName = organizerName;
	}

	public ReportEvent(Integer eventId, String eventName, String startDateTime, String endDateTime,
			Integer numHours, String organizerName, Integer categoryId, String eventStatus) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.numHours = numHours;
		this.organizerName = organizerName;
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

	public List<Volunteer> getVolunteers() {
		return volunteers;
	}

	public void setVolunteers(List<Volunteer> volunteers) {
		this.volunteers = volunteers;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public Integer getNumHours() {
		return numHours;
	}

	public void setNumHours(Integer numHours) {
		this.numHours = numHours;
	}

	@Override
	public String toString() {
		return "ReportEvent [eventId=" + eventId + ", eventName=" + eventName + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", numParticipants=" + numParticipants + ", volunteers=" + volunteers
				+ ", categoryId=" + categoryId + ", eventStatus=" + eventStatus + ", organizerName=" + organizerName
				+ ", numHours=" + numHours + "]";
	}

}
