package citi.hackathon.entity;

public class CreateEventRequestBody {
	private String eventName;
	private String startDateTime;
	private String endDateTime;
	private String organizerName;
	private Integer categoryId;
	private String description;
	private Integer maxParticipants;
	private Integer minParticipants;
	private String eventStatus;
	
	public CreateEventRequestBody() {}

	public CreateEventRequestBody(String eventName, String startDateTime, String endDateTime, String organizerName,
			Integer categoryId, String description, Integer maxParticipants, Integer minParticipants,
			String eventStatus) {
		this.eventName = eventName;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.organizerName = organizerName;
		this.categoryId = categoryId;
		this.description = description;
		this.maxParticipants = maxParticipants;
		this.minParticipants = minParticipants;
		this.eventStatus = eventStatus;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(Integer maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public Integer getMinParticipants() {
		return minParticipants;
	}

	public void setMinParticipants(Integer minParticipants) {
		this.minParticipants = minParticipants;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Override
	public String toString() {
		return "CreateEventRequestBody [eventName=" + eventName + ", startDateTime=" + startDateTime + ", endDateTime="
				+ endDateTime + ", organizerName=" + organizerName + ", categoryId=" + categoryId + ", description="
				+ description + ", maxParticipants=" + maxParticipants + ", minParticipants=" + minParticipants
				+ ", eventStatus=" + eventStatus + "]";
	}
	
}
