package citi.hackathon.entity;

import java.util.List;

public class BreakdownReport {
	private List<ReportEvent> events;
	
	public BreakdownReport () {}

	public BreakdownReport(List<ReportEvent> events) {
		this.events = events;
	}

	public List<ReportEvent> getEvents() {
		return events;
	}

	public void setEvents(List<ReportEvent> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "BreakdownReport [events=" + events + "]";
	}
}
