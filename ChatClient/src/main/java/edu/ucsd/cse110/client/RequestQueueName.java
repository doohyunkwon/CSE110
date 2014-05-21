package edu.ucsd.cse110.client;

import java.io.Serializable;

import javax.jms.Queue;

public class RequestQueueName implements Serializable {
	private String userName;

	public RequestQueueName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
