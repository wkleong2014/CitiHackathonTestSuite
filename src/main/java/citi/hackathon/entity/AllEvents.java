package citi.hackathon.entity;

import java.util.List;

public class AllEvents {
	private List<Event> events;
	
	public AllEvents() {}

	public AllEvents(List<Event> events) {
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
		return "AllEvents [events=" + events + "]";
	}
	
}
