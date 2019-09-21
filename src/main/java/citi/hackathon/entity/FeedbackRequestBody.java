package citi.hackathon.entity;

public class FeedbackRequestBody {
	private Integer userId;
	private String feedback;
	
	public FeedbackRequestBody() {}
	
	public FeedbackRequestBody(Integer userId, String feedback) {
		super();
		this.userId = userId;
		this.feedback = feedback;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "FeedbackRequestBody [userId=" + userId + ", feedback=" + feedback + "]";
	}
	
}
