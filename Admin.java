
// Note: This is an active class and must implemnet runnable 
public class Admin implements Runnable{
	
	private static int sleepScale = 100;

	private String name;
	private ChatServer chatServer; 
	private int numOfActions = 15;  //Could be either openning or closing a chatroom

	public Admin(String name) {
		// Set the initial value of class variables
		this.name = name;
	}

	public void assignServer(ChatServer chatServer) {
		// Store given Chat Server in Class Attribute
		this.chatServer = chatServer;
	}
	
	// Does this class require a run() method? If so consider how to ensure
	// when the thread is run that it performs all required actions.

	public void run() {
		
		// you need to open the chat server and the chat rooms
		chatServer.open();
		//opening the chatroom
		chatServer.openChatRoom(0);

		// run actions randomly (HINT: you may use a randomised sleep time before doing the action)

		for (int i = 0; i < numOfActions; i++) {
			//sleep for a random number of time
			try {
				Thread.sleep((int) (Math.random() * 2000 - 1000 + 1) + 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//randomly chooing opening and closing a chat room
			if (Math.random() < 0.5) {
				//open
				int chatroomID = (int) (Math.random() * chatServer.getNumberOfRooms());
				chatServer.openChatRoom(chatroomID);
			} else {
				//close a random chat
				int chatRoomID = (int) (Math.random() * chatServer.getNumberOfRooms());
				chatServer.openChatRoom(chatRoomID);

			}
		}
			//close the chat server and the chat rooms
		for (int i = 0; i < chatServer.getNumberOfRooms(); i++) {
			chatServer.closeChatRoom(i);
		}
		//closing the chat server
		chatServer.close();
	}

}