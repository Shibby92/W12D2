package Server;

import java.util.LinkedList;
import java.util.Queue;

public class Message {


	private String content;
	private String sender;
	public static volatile Queue<Message> msgQueue = new LinkedList<Message>();

	public Message(String content, String sender) {
		super();
		this.content = content+"\n";
		this.sender = sender;
		msgQueue.add(this);
	}
	public String getSender() {
		return sender;
	}
	public String getContent() {
		return content;
	}
	public static boolean hasNext(){
		return !msgQueue.isEmpty();
	}
	public static Message next(){
		return msgQueue.poll();
	}

}
