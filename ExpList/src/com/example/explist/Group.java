package com.example.explist;

import java.util.ArrayList;


public class Group {

	private String eventDay;
	private ArrayList<EventItem> eventList = new ArrayList<EventItem>();
	public String getEventDay() {
		return eventDay;
	}
	public void setEventDay(String eventDay) {
		this.eventDay = eventDay;
	}
	public ArrayList<EventItem> getEventList() {
		return eventList;
	}
	public void setEventList(ArrayList<EventItem> eventList) {
		this.eventList = eventList;
	}



}
