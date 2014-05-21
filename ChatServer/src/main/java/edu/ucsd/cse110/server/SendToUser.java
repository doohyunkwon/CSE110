package edu.ucsd.cse110.server;

import java.io.Serializable;

public class SendToUser implements Serializable{
	private String recipient;
	private String msg;
	
	public SendToUser(String recipient, String msg) {
		super();
		this.recipient = recipient;
		this.msg = msg;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}