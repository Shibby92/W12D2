package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import GUI.ChatGui;

public class Server {
	
	public static final int port = 1717;

	public static void serverStart() throws IOException{
		ServerSocket server = new ServerSocket(port);
		
		while(true){
			String str = "waiting for connection";
			System.out.println(str);
			Socket client = server.accept();
			ChatGui gui = new ChatGui(client);
			new Thread(gui).start();
		}
	}
	
	public static void main(String[] args) {
		try {
			serverStart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
