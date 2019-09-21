package citi.hackathon.entity;

public class Feedback {
	private Integer feedbackId;
	private Integer userId;
	private Integer eventId;
	private String feedback;
	
	public Feedback() {}
	
	public Feedback(Integer feedbackId, Integer userId, Integer eventId, String feedback) {
		this.feedbackId = feedbackId;
		this.userId = userId;
		this.eventId = eventId;
		this.feedback = feedback;
	}

	public Integer getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
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

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "Feedback [feedbackId=" + feedbackId + ", userId=" + userId + ", eventId=" + eventId + ", feedback="
				+ feedback + "]";
	}
	
}
