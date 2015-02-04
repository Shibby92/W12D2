package Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import GUI.ChatGui;

public class Client {
	public static final int port = 1717;
	public static final String host = "10.0.82.62";
	
	
	public static void main(String[] args) {
		
		LoginGUI test = new LoginGUI(host, port);
	}
}
