package citi.hackathon.entity;

import java.util.List;

public class UserHistoricalBreakdownReport {
	private Integer numEvents;
	private Integer totalHours;
	private String fromDate;
	private String toDate;
	private List<ReportEvent> events;

	public UserHistoricalBreakdownReport() {
	}

	public UserHistoricalBreakdownReport(Integer numEvents, Integer totalHours, String fromDate, String toDate,
			List<ReportEvent> events) {
		this.numEvents = numEvents;
		this.totalHours = totalHours;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.events = events;
	}

	public Integer getNumEvents() {
		return numEvents;
	}

	public void setNumEvents(Integer numEvents) {
		this.numEvents = numEvents;
	}

	public Integer getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<ReportEvent> getEvents() {
		return events;
	}

	public void setEvents(List<ReportEvent> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "UserHistoricalBreakdownReport [numEvents=" + numEvents + ", totalHours=" + totalHours + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", events=" + events + "]";
	}
}
