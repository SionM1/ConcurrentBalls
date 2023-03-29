import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
public class ChatServer {
	
	private ArrayList<ChatRoom> rooms;
	private List<User> users;
	private Admin admin;

	private int capacity;
	private boolean isOpen;

	public ChatServer(int capacity, int numOfRooms, Admin admin) {
		// Set the initial value of class variables.
		// Think carefully about how to protect users from
		// unintended synchronous activity.

		// We initalise the admin attribute and call the 
		// assignServer method of the admin with this object 
		// as the parameter.  
		this.admin = admin;
		admin.assignServer(this);

		this.rooms = new ArrayList<ChatRoom>();
		for (int i = 0; i < numOfRooms; i++) {
			this.rooms.add(new ChatRoom(i))
			;
		}

		this.users = Collections.synchronizedList(new ArrayList<User>());
		this.capacity = capacity;
		this.isOpen = false;

	}

	// Consider if this should be run asynchronously.
	public synchronized void open() {
		// Code to open the Chat Room.
		if (!isOpen) {
			isOpen = true;
			System.out.println("Chat Server is Opened.");
		}
	}
	
	// Consider if this should be run asynchronously.
	public synchronized void close() {
		// Code to close the Chat Server.
		// Think carefully about when you can successfully
		if (users.isEmpty()) {
			// close the Chat Server.
			isOpen = false;
			System.out.println("Chat Server is being Closed.");
			System.out.println("Chat Server is Closed.");
			return;
		}
		System.out.println("cant close the chat server as there are still users in it.");
	}

	// Consider if this should be run asynchronously.
	public synchronized boolean join(User user) {
		// Code for a User to enter the Chat Server.
		// Consider conditions that need to be true for this 
		// to be successful.
		// Returns true if joined successfully, false otherwise.
		if (users.size() >= capacity) {
			System.out.println("User " + user.getID() + " failed to join Chat Server (" + user.getWantToChat() + ").");
			return false;
		}
		users.add(user);
		System.out.println("User " + user.getID() + " admitted to Chat Server (" + user.getWantToChat() + ").");
		return true;

	}


	// Consider if this should be run asynchronously.
	public synchronized void leave(User user) {
		// Code for a User to leave the Chat Server.
		if (!users.contains(user)) {
			System.out.println("Could not remove User " + user.getID() + " as is not in the Chat Server.");
			return;
		}
		users.remove(user);
		System.out.println("User " + user.getID() + " left the Chat Server.");

	}

	public synchronized void openChatRoom(int chatRoomID) {
		// Code to open Chat Room.
		if (chatRoomID >= rooms.size()) {
			System.out.println("incorrect room ID");
			return;
		}
		rooms.get(chatRoomID).open();
	}

	public synchronized void closeChatRoom(int chatRoomID) {
		// Code to close Chat Room.
		if (chatRoomID >= rooms.size()) {
			System.out.println("incorrect room ID");
			return;
		}
		rooms.get(chatRoomID).close();
	}


	public synchronized boolean enterRoom(User user, int chatRoomID) {
		// Code to allow user to enter Chat Room.
		if (chatRoomID >= rooms.size()) {
			System.out.println("incorrect room ID");
			return false;
		}
		return rooms.get(chatRoomID.addUser(user));
	}

	public synchronized void leaveRoom(User user, int chatRoomID) {
		// Code to allow user to leave Chat Room.
		if (chatRoomID >= rooms.size()) {
			System.out.println("incorrect room ID");
			return;
		}
		return rooms.get(chatRoomID).addUser(user);
	}
	public int getNumberOfRooms() {
		return rooms.size();
	}

	public boolean isRoomOpen(int chatRoomID) {
		if (chatRoomID >= rooms.size()) {
			System.out.println("incorrect room ID");
			return false;
		}
		return rooms.get(chatRoomID).getIsOpen();
	}

	public int getNumberOfUsers() {
		return users.size();
	}

}
