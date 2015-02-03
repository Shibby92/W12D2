package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConnectionListener extends Thread {
	private InputStream is;
	private String sender;

	public ConnectionListener(String name, InputStream is) {
		super();
		this.is = is;
		this.sender = name;
	}

	public void run() {
		BufferedReader bf = new BufferedReader(new InputStreamReader(this.is));
		try {
			String str="";
			while ((str = bf.readLine()) != null) {
				if (!str.equals("")) {
				Message incoming =new	Message(str,sender);
				System.out.println(incoming.getSender()+": "+incoming.getContent());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
