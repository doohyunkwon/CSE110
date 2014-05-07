package edu.ucsd.cse110.server;

import edu.ucsd.cse110.server.db.DataElement;
import edu.ucsd.cse110.server.db.DataElementRepository;
import edu.ucsd.cse110.server.db.UserFriends;
import edu.ucsd.cse110.server.db.UserFriendsRepository;
import edu.ucsd.cse110.server.db.UserQueue;
import edu.ucsd.cse110.server.db.UserQueueRepository;

public class Server {

	private UserQueueRepository userQueueRepository;
	private UserFriendsRepository userFriendsRepository;

	public Server() {}

	public Server(UserQueueRepository userQueueRepo) {
		super();
		this.userQueueRepository = userQueueRepo;
	}

	public Server(UserFriendsRepository userFriendsRepo) {
		super();
		this.userFriendsRepository = userFriendsRepo;
	}


	public void receive(String msg) {
		System.out.println("Message received: " + msg);

	}

	public void receive(SendToUser msg) {
		String recipient = msg.getRecipient();
		String message = msg.getMsg();

		Iterable<UserQueue> userQueues = userQueueRepository.findAll();
		userQueues = userQueueRepository.findByName(recipient);
		while(userQueues.iterator().hasNext()) {
			UserQueue userQueue = userQueues.iterator().next();
			userQueue.getQueue().add(message);
		}

	}

	public void receive(AddFriend add) {
		String user1 = add.getUser1();
		String user2 = add.getUser2();

		Iterable<UserFriends> userFriends1 = userFriendsRepository.findAll();
		userFriends1 = userFriendsRepository.findByName(user1);

		while(userFriends1.iterator().hasNext()) {
			UserFriends userFriend = userFriends1.iterator().next();
			userFriend.getFriendsList().add(user2);
		}
		
		Iterable<UserFriends> userFriends2 = userFriendsRepository.findAll();
		userFriends2 = userFriendsRepository.findByName(user2);

		while(userFriends2.iterator().hasNext()) {
			UserFriends userFriend = userFriends2.iterator().next();
			userFriend.getFriendsList().add(user1);
		}

	}

}
