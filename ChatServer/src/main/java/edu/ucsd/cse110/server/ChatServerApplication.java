package edu.ucsd.cse110.server;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import edu.ucsd.cse110.server.db.DataElement;
import edu.ucsd.cse110.server.db.DataElementRepository;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ChatServerApplication {

	@Bean
	ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(new ActiveMQConnectionFactory(
				Constants.USERNAME, Constants.PASSWORD, Constants.ACTIVEMQ_URL));
	}

	@Bean
	MessageListenerAdapter receiver() {
		return new MessageListenerAdapter(new Server()) {
			{
				setDefaultListenerMethod("receive");
			}
		};
	}

	@Bean
	SimpleMessageListenerContainer container(
			final MessageListenerAdapter messageListener,
			final ConnectionFactory connectionFactory) {
		return new SimpleMessageListenerContainer() {
			{
				setMessageListener(messageListener);
				setConnectionFactory(connectionFactory);
				setDestinationName(Constants.QUEUENAME);
			}
		};
	}

	@Bean
	JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
		return new JmsTemplate(connectionFactory);
	}

	public static void main(String[] args) throws Throwable {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				ChatServerApplication.class);
		MessageCreator messageCreator = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("ping!");
			}
		};
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		System.out.println("Sending a new message: ping!");
		jmsTemplate.send(Constants.QUEUENAME, messageCreator);
		// Wait 10 seconds
		Thread.sleep(10000);
		// Try the database
		playWithDB(context);
		// Close the application
		context.close();
	}

	private static void playWithDB(AnnotationConfigApplicationContext context) {
		// Play with the database
		DataElementRepository repository = context
				.getBean(DataElementRepository.class);
		repository.save(new DataElement("Test", 10));
		repository.save(new DataElement("Other", 1));
		repository.save(new DataElement("Other", 5));
		// List all elements in the database
		Iterable<DataElement> elements = repository.findAll();
		System.out.println("Elements found with findAll():");
		printElements(elements);
		// Find elements with a given name
		elements = repository.findByName("Other");
		System.out.println("Elements found with findByName(\"Other\"):");
		printElements(elements);
	}

	private static void printElements(Iterable<DataElement> elements) {
		System.out.println("-------------------------------");
		for (DataElement element : elements) {
			System.out.println(element);
		}
		System.out.println();
	}
}
