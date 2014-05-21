package edu.ucsd.cse110.server;
import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.io.ObjectOutputStream;

import java.io.Serializable;
import java.net.URISyntaxException;

import java.util.HashMap;

import java.util.Map;



import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import javax.jms.MessageConsumer;

import javax.jms.MessageProducer;

import javax.jms.Queue;

import javax.jms.Session;

import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;




import edu.ucsd.cse110.server.db.DataElement;

import edu.ucsd.cse110.server.db.DataElementRepository;





public class Server implements Serializable{


	/**

	 * 

	 */

	private static final long serialVersionUID = 1L;

	private DataElementRepository database;

	private Connection conn;

	private MessageProducer producer;

	private HashMap<String, String> userQueueNames;

	private Session session;

	private JmsTemplate temp;


	public Server(JmsTemplate temp) throws JMSException, URISyntaxException

	{
		this.temp = temp;
		this.userQueueNames = new HashMap<String, String>();
	}


	public void receive(String msg) {

		System.out.println("Message received: " + msg);

	}


	//receive just receives objects in general, multiple 
	//if statements for the different objects received
	public void receive(Message message) throws IOException, JMSException{

		if(message instanceof RequestQueueName) {
			String userName = ((RequestQueueName) message).getUserName();
			int id = 0;
			String queueName = null;

			if(this.userQueueNames.containsKey(userName)) {
				queueName = this.userQueueNames.get(userName);
			}
			else {
				
				if(this.userQueueNames.isEmpty()) {
					id = 1;
				}
				else id = this.userQueueNames.size()+1;

				queueName = Constants.QUEUENAME+id;

				this.userQueueNames.put(userName, queueName);

				this.temp.getConnectionFactory().createConnection().createSession(false,
						Session.AUTO_ACKNOWLEDGE).createQueue(queueName);
			}

			final ResponseQueueName response = new ResponseQueueName(userName, queueName);
			
			MessageCreator messageCreator = new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(response);
				}
			};

			this.temp.send(Constants.QUEUENAME, messageCreator);

		}




	}






	//	public void receive(SendToUser stu) throws JMSException, IOException {
	//
	//		String recipientUserName=stu.getRecipientUserName();
	//
	//		Queue recipientQueue=null;
	//
	//		Iterable<DataElement> elements = this.database.findAll();
	//
	//		elements = this.database.findByName(recipientUserName);
	//
	//		if(elements.iterator().hasNext()) {
	//
	//			//recipientQueue=elements.iterator().next().getQueue();
	//
	//		}
	//
	//		MessageProducer producer = this.session.createProducer(recipientQueue);
	//
	//		producer.send(session.createTextMessage(stu.getMsg()));
	//
	//	}



	static private class CloseHook extends Thread {
		ActiveMQConnection connection;
		private CloseHook(ActiveMQConnection connection) {
			this.connection = connection;
		}

		public static Thread registerCloseHook(ActiveMQConnection connection) {
			Thread ret = new CloseHook(connection);
			Runtime.getRuntime().addShutdownHook(ret);
			return ret;
		}

		public void run() {
			try {
				System.out.println("Closing ActiveMQ connection");
				connection.close();
			} catch (JMSException e) {
				/*
				 * This means that the connection was already closed or got
				 * some error while closing. Given that we are closing the
				 * client we can safely ignore this.
				 */
			}
		}
	}


}

