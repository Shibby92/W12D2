package Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import GUI.ChatGui;

public class Client {
	public static final int port = 1717;
	public static final String host = "localhost";
	
	
	public static void main(String[] args) {
		
		try {
			
			Socket client = new Socket(host, port);
			System.out.println("Unesi svoje ime: ");
			Scanner s = new Scanner(System.in);
			String name= s.nextLine()+"\n";
			client.getOutputStream().write(name.getBytes());
			client.getOutputStream().flush();
			ChatGui gui = new ChatGui(client);
			new Thread(gui).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
