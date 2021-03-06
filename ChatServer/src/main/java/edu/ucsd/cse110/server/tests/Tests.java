//package edu.ucsd.cse110.server.tests;
//
//
//import static org.junit.Assert.*;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
//import javax.jms.ConnectionFactory;
//
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.listener.SimpleMessageListenerContainer;
//import org.springframework.jms.listener.adapter.MessageListenerAdapter;
//
//import edu.ucsd.cse110.server.ChatServerApplication;
//import edu.ucsd.cse110.server.Constants;
//import edu.ucsd.cse110.server.SendToUser;
//import edu.ucsd.cse110.server.Server;
//import edu.ucsd.cse110.server.db.UserQueue;
//import edu.ucsd.cse110.server.db.UserQueueRepository;
//
//
//
//
//public class Tests {
//	
//	@Test
//	public void test() {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//				ChatServerApplication.class);
//		
//		UserQueueRepository repository = context
//				.getBean(UserQueueRepository.class);
//		
//		Queue<String> msgQueue = new LinkedList<String>();
//		repository.save(new UserQueue("bob", msgQueue));
//		
//		
//		SendToUser message = new SendToUser("bob", "hi");
//		Server server = new Server(repository);
//		server.receive(message);
//		
//		String queueMsg = null;
//		
//		Iterable<UserQueue> userQueues = repository.findAll();
//		while(userQueues.iterator().hasNext()) {
//			UserQueue userQueue = userQueues.iterator().next();
//			queueMsg = userQueue.getQueue().peek();
//		}
//		
//		assertEquals("hi", queueMsg);
//		
//	
//	
//	}
//
//}
