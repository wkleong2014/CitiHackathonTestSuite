package citi.hackathon.entity;

import java.util.List;

public class OrganizationBreakdownReport {
	private List<Event> events;

	public OrganizationBreakdownReport(List<Event> events) {
		super();
		this.events = events;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "OrganizationBreakdownReport [events=" + events + "]";
	}
}
