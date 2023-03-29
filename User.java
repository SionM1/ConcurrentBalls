
// Is this a passive or active class?
public class User implements Runnable {

	private static int sleepScale = 100;
	
	private int userID;
	private ChatServer chatServer;

	private boolean joinedServer;
	private boolean joinedMainRoom;

	private int wantToChat;

	public User(int userID, ChatServer chatServer) {
		// Set the initial value of class variables.
		// Set wantToChat to random value in range
		// of 10 to 15.
		// Int Range (MAX, MIN) -> (int)Math.random() * (MAX-MIN+1) + MIN
		//sets the value to the value passed in as a parameter to the constructor
		this.userID = userID;
		//sets value to the value passed in as a parameter to the constructor
		this.chatServer = chatServer;
		this.joinedServer = false;
		this.joinedMainRoom = false;
		this.wantToChat = (int)(Math.random() * (15-10+1) + 10);
	}

	public int getWantToChat() {
		return wantToChat;
	}

	public int getID() {
		return userID;
	}

	// Within the run method we need to code the different actions
	// a user will take when started.
	public void run() {
		try {
			// While the user is still interested in chatting ...
			while (wantToChat > 0) {
				if (!joinedServer) {
					// Try and join Chat Server ...
					// Reduce wantToChat?
					joinedServer = chatServer.join(this);
					wantToChat--;

					// What should the user try and do next?
				} else if (!joinedMainRoom) {
					// Try and join Main Chat Room
					joinedMainRoom = chatServer.enterRoom(this, 0);
					// What is the final action the user should keep
					wantToChat--;
					// attempting to do?
				} else {
					// Try and join a random Chat Room
					int chatRoomID = (int) (Math.random() * chatServer.getNumberOfRooms());
					chatServer.enterRoom(this, chatRoomID);
					wantToChat--;
				}
			}
			Thread.sleep((int) (Math.random() * sleepScale));
			// What should happen when the user no longer wants to chat?
			if (joinedMainRoom) {
			chatServer.leaveRoom(this, 0);
			}
			if (joinedServer) {
			chatServer.leave(this);
			}
		} catch (InterruptedException ex) {
			System.out.println("Interrupted User Thread (" + userID + ")");
		}
		System.out.println("User Thread (" + userID + ") has ended!");
	}
}