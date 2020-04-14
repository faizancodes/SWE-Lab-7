import java.awt.Dimension;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class VehicularCloudManager
{
	
	private int vcManagerID;
	private String vcManagerName;
	
	static ServerSocket serverSocket;
	static Socket socket;
	static DataInputStream inputStream;
	static DataOutputStream outputStream;
	
	
	public VehicularCloudManager(int vcManagerID, String vcManagerName) {
		this.vcManagerID = vcManagerID;
		this.vcManagerName = vcManagerName;
	}

	public int getVCManagerID() {
		return vcManagerID;
	}

	public String getVCManagerName() {
		return vcManagerName;
	}
	
	public static void main(String[] args)
	{
		String messageIn = "";
		String messageOut = "";
		Scanner keyInput;
		
		try {

			System.out.println("----------$$$ This is server side $$$--------");
			System.out.println("wating for client to connect...");
			
			// creating the server
			serverSocket = new ServerSocket(9806);
			
			// sever accepts connection request from client
			socket = serverSocket.accept();
			System.out.println("client is connected!");
			System.out.println("go to client side and send me a message");

			// server reads a message message from client
			inputStream = new DataInputStream(socket.getInputStream());

			// server sends a message to client
			outputStream = new DataOutputStream(socket.getOutputStream());

			// as long as message is not exit keep reading and sending message to client
			while (!messageIn.equals("exit")) 
			{

				// extract the message from client
				messageIn = inputStream.readUTF();
				// server prints the message received from client to console
				System.out.println("message received from client: " + "\"" + messageIn + "\"");

				
				if (messageIn.equals("send data"))
				{
					JFrame frame = new JFrame();
					frame.setSize(700, 600);
					
					JPanel panel = new JPanel();
				        panel.setLayout(null);
					
				        JLabel idLabel = new JLabel("Client wants to send data");
				        idLabel.setBounds(250, 10, 300, 300);
				    
			                JButton acceptButton = new JButton("Accept Data");
			         	JButton rejectButton = new JButton("Reject Data");

			        	acceptButton.setBounds(100,200,200,100);
			        	rejectButton.setBounds(350,200,200,100);
			        
			        	panel.add(idLabel);
					panel.add(acceptButton);
					panel.add(rejectButton);

					frame.add(panel);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);

					acceptButton.addActionListener(new java.awt.event.ActionListener() 
					{
					    @Override
					    public void actionPerformed(java.awt.event.ActionEvent evt) 
					    {
						System.out.println("Accepted data");	
						frame.setVisible(false);

						try {
							outputStream.writeUTF("VCCloudManager has accepted the data");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					    }
					});

					rejectButton.addActionListener(new java.awt.event.ActionListener() 
					{
					    @Override
					    public void actionPerformed(java.awt.event.ActionEvent evt)
					    {
						System.out.println("Rejected data");	
						frame.setVisible(false);

						try {
							outputStream.writeUTF("VCCloudManager has rejected the data");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    }
					});
				}
				
				else 
				{
					// ********************************************************
					// server reads a message from keyboard
					System.out.println("Enter a message you want to send to client side: ");
					keyInput = new Scanner(System.in);
					messageOut = keyInput.nextLine();
					
					// server sends the message to client
					outputStream.writeUTF(messageOut);
				}	
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
}
