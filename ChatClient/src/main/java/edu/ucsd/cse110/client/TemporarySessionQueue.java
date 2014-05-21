package edu.ucsd.cse110.client;

import java.io.Serializable;

import javax.jms.Queue;

public class TemporarySessionQueue implements Serializable {
	
	private String userName;
	private Queue temporarySessionQueue;
	
	public TemporarySessionQueue(String userName, Queue temporarySessionQueue) {
		this.userName = userName;
		this.temporarySessionQueue = temporarySessionQueue;
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Queue getTemporarySessionQueue() {
		return temporarySessionQueue;
	}

	public void setTemporarySessionQueue(Queue temporarySessionQueue) {
		this.temporarySessionQueue = temporarySessionQueue;
	}


	
}
