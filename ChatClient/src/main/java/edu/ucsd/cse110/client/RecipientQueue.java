package edu.ucsd.cse110.client;

import java.io.Serializable;

import javax.jms.Queue;

public class RecipientQueue implements Serializable {
	
	private Queue queueName;
	private String userMessage;
	
	public RecipientQueue(Queue queueName, String userMessage) {
		this.queueName = queueName;
		this.userMessage = userMessage;
		
	}

	public Queue getqueueName() {
		return queueName;
	}

	public void setqueueName(Queue queueName) {
		this.queueName = queueName;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	
	
}