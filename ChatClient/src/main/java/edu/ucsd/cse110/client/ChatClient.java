package edu.ucsd.cse110.client;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import edu.ucsd.cse110.client.Constants;
import edu.ucsd.cse110.client.RequestQueueName;
import edu.ucsd.cse110.client.ResponseQueueName;

public class ChatClient {

	private JmsTemplate temp;
	private String userName;
	
	public ChatClient(JmsTemplate temp) {
		super();
		this.temp = temp;
		
	} 
	
	
	public void send(final RequestQueueName rqn) throws JMSException {
		MessageCreator messageCreator = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(rqn);
			}
		};
		
		this.temp.send(Constants.QUEUENAME, messageCreator);
		
	}
	
	public void receive(Message message) throws IOException, JMSException{

		if(message instanceof ResponseQueueName) {
			String userName = ((ResponseQueueName) message).getUserName();
			
			if(!this.userName.equalsIgnoreCase(userName)) {
				break;
			} 
			else {
				
				
				
			}



		}

	
	
	
	
	
	
	
	
	
	
	
}


	public JmsTemplate getTemp() {
		return temp;
	}


	public void setTemp(JmsTemplate temp) {
		this.temp = temp;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
