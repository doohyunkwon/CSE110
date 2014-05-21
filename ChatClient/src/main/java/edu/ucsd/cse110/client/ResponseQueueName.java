package edu.ucsd.cse110.client;

import java.io.Serializable;

public class ResponseQueueName implements Serializable {

	private String userName;
	private String queueName;
	
	public ResponseQueueName(String userName, String queueName) {
		this.userName = userName;
		this.queueName = queueName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	
}