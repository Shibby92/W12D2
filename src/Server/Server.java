package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import GUI.ChatGui;

public class Server {

	public static final int port = 1717;

	public static void serverStart() throws IOException {
		ServerSocket server = new ServerSocket(port);
		ConnectionWriter cw = new ConnectionWriter();
		cw.start();
		while (true) {
			String str = "waiting for connection";
			System.out.println(str);

			try {
				Socket client = server.accept();
				String clientName = handShake(client.getInputStream());
				if (clientName != null) {
					while (ConnectionWriter.connections.containsKey(clientName)) {
						clientName += new Random().nextInt(1000);
					}
					ConnectionWriter.connections.put(clientName,
							client.getOutputStream());
					ConnectionListener cl = new ConnectionListener(clientName,
							client.getInputStream());
					cl.start();
					new Message ("join%"+ clientName, "%server%");
					client.getOutputStream().write(0);
					
				}
				else{
					client.getOutputStream().write(-1);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	}

	public static void main(String[] args) {
	
			try {
				new XMLConnection();
			} catch (ParserConfigurationException | SAXException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		try {
			serverStart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String handShake(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = br.readLine();
		line= line.replaceAll("%", "");
		String password= br.readLine();
		int result;
		try {
			result = XMLConnection.userLogin(line, password);
			if(result==0){
			return null;
		}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return line;
	}

}
