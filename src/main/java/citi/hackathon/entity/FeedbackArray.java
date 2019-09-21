package citi.hackathon.entity;

import java.util.List;

public class FeedbackArray {
	private Integer eventId;
	private List<Feedback> feedbacks;
	
	public FeedbackArray() {}
	
	public FeedbackArray(Integer eventId, List<Feedback> feedbacks) {
		this.eventId = eventId;
		this.feedbacks = feedbacks;
	}
	
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@Override
	public String toString() {
		return "FeedbackArray [eventId=" + eventId + ", feedbacks=" + feedbacks + "]";
	}
	
}
