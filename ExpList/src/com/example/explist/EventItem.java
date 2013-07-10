package com.example.explist;

public class EventItem {
	
	private String itemName;
	private String itemTime;

	public EventItem(String itemName, String itemTime) {
		this.setItemName(itemName);
		this.setItemTime(itemTime);

	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemTime() {
		return itemTime;
	}

	public void setItemTime(String itemTime) {
		this.itemTime = itemTime;
	}

}
