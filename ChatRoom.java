import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
public class ChatRoom {
	
	public int chatRoomID;
	public int capacity;
	
	public List<User> users;
	public boolean isOpen;

	public ChatRoom(int chatRoomID, int capacity) {
		// Set the initial value of class variables.
		this.chatRoomID = chatRoomID;
		this.capacity = capacity;
		this.users = Collections.synchronizedList(new ArrayList<User>());
		this.isOpen = false;


		// Think carefully about how to protect users from
		// unintended synchronous activity.
	}

	// Consider if this should be run asynchronously.
	public synchronized void Open() {
		isOpen = true;
		System.out.println("Chat Room " + chatRoomID + " open!");
	}
	
	// Consider if this should be run asynchronously.
	public synchronized void close() {
		System.out.println("Chat Room " + chatRoomID + " is being closed!");
		// Code to close the Chat Room.
		if (users.isEmpty()) {
			isOpen = false;
			System.out.println("Chat Room " + chatRoomID + " closed!");
		}else {
			System.out.println("Chat Room " + chatRoomID + " cannot be closed as there are users in there.");
		}
	}

	// Consider if this should be run asynchronously.
	// Consider conditions that need to be true for this
	// to be successful.
	// Returns true if joined successfully, false otherwise.
	public synchronized boolean enterRoom(User user) {
		// Code for a User to enter a Chat Room.
		if (isOpen && users.size() < capacity) {
			users.add(user);
			System.out.println("User " + user.getID() + " joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
			return true;
		} else {
			System.out.println("User " + user.getID() + " not joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
			return false;
		}
	}

	// Consider if this should be run asynchronously.
	public synchronized void leaveRoom(User user) {
		// Code for a User to leave a Chat Room.
		if (users.contains(user)) {
			users.remove(user);
			System.out.println("User " + user.getID() + " left Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
		} else {
			System.out.println("User " + user.getID() + " is not in Chat Room " + chatRoomID);
		}
	}
	public boolean getIsOpen() {
		return isOpen;
	}
}