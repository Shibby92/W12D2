package Server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ConnectionWriter extends Thread {
	public static HashMap<String, OutputStream> connections = new HashMap<String, OutputStream>();
	private Set<String> set = connections.keySet();
	private Iterator<String> it;

	public void run() {
		while (true) {
			if (Message.hasNext()) {
				it= set.iterator();
				Message msg = Message.next();
				byte[] msgByte = ((msg.getSender() + ": " + msg.getContent())
						.getBytes());
				while (it.hasNext()) {
					String temp = it.next();
					if (!temp.equals(msg.getSender())) {
						OutputStream os = connections.get(temp);
						try {
							os.write(msgByte);
							os.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}
	}

}
