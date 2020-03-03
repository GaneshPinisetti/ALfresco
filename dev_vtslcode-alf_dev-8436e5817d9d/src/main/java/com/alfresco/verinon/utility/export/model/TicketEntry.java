package com.alfresco.verinon.utility.export.model;

public class TicketEntry {
	private Ticket entry;

	public Ticket getEntry() {
		return entry;
	}

	public void setEntry(Ticket entry) {
		this.entry = entry;
	}

	@Override
	public String toString() {
		return "TicketEntry [entry=" + entry + "]";
	}
	
}
