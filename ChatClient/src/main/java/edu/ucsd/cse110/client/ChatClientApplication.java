package edu.ucsd.cse110.client;

import java.net.URISyntaxException;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
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



@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ChatClientApplication {

	@Bean
	ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(new ActiveMQConnectionFactory(
				Constants.USERNAME, Constants.PASSWORD, Constants.ACTIVEMQ_URL));
	}

	@Bean
	MessageListenerAdapter receiver(JmsTemplate temp) throws JMSException, URISyntaxException {
		return new MessageListenerAdapter(new ChatClient(temp)) {
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
				ChatClientApplication.class);
		
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		ChatClient client = new ChatClient(jmsTemplate);
		RequestQueueName rqn = new RequestQueueName("Byron");
		client.send(rqn);
		

		// Wait 10 seconds
		Thread.sleep(10000);
		// Close the application
		context.close();
	}
}
