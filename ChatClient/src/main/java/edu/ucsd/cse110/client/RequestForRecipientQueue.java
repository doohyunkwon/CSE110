package edu.ucsd.cse110.client;

import java.io.Serializable;

import javax.jms.Queue;

public class RequestForRecipientQueue implements Serializable {
	
	private String recipientUserName;
	private String userMessage;
	
	public RequestForRecipientQueue(String recipientUserName, String userMessage) {
		this.recipientUserName = recipientUserName;
		this.userMessage = userMessage;
		
	}

	public String getRecipientUserName() {
		return recipientUserName;
	}

	public void setRecipientUserName(String recipientUserName) {
		this.recipientUserName = recipientUserName;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	
	
}
