package Client;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import GUI.ChatGui;

public class LoginGUI {
	private JTextField username;
	private JTextField password;
private String host;
private int port;

	public LoginGUI(String host , int port) {
		this.host= host;
		this.port= port;
		username = new JTextField();
		password = new JPasswordField();
		JFrame window = new JFrame("Login");
		window.setSize(400, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		JPanel panel = new JPanel();
		window.setContentPane(panel);
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setPreferredSize(new Dimension(390, 50));
		panel.add(usernameLabel);
		username.setPreferredSize(new Dimension(390, 50));
		panel.add(username);
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setPreferredSize(new Dimension(390, 50));
		panel.add(passwordLabel);
		password.setPreferredSize(new Dimension(390, 50));
		panel.add(password);
		JButton login = new JButton("Login");
		login.setPreferredSize(new Dimension(180, 50));
		login.addActionListener(new ButtonHandler());
		panel.add(login);
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});
		quit.setPreferredSize(new Dimension(180, 50));
		panel.add(quit);
		window.setVisible(true);

	}

	private class ButtonHandler extends KeyAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String user=username.getText();
			String pass=new String(password.getText());
			user.replaceAll(" ", "");
			pass.replaceAll(" ", "");
			System.out.println(user);
			System.out.println(pass);
		if(user.length()==0 || pass.length()==0){
			showError("Ispunite username/password");
			return;
		}
		Socket client;
		try {
			client = new Socket(host, port);
			OutputStream os = client.getOutputStream();
			InputStream is= client.getInputStream();
			os.write((user+"\n").getBytes());
			os.write((pass+"\n").getBytes());
			int result= is.read();
			if( result==0){
				ChatGui gui = new ChatGui(client);
			new Thread(gui).start();
			}
			
			else{
				showError("Username/password nije dobar");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		}
	}
	private void showError(String param){
		JOptionPane.showMessageDialog(null, param,"ERROR",JOptionPane.WARNING_MESSAGE);
		
	
	}

}
