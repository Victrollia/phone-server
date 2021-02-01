//Student: Victoria Cadogan
import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.*;

public class PhoneServer {
	// The port number on which the server will be listening
	private static int port = 2014;
	// The server socket
	private static ServerSocket listener = null;
	// The client socket
	private static Socket clientSocket = null;

	public static void main(String[] args) throws Exception {
		boolean listening = true;
		try {
			listener = new ServerSocket(port);
			System.out.println(" PhoneServer is up and running\n");
			while (listening) {
				new ClientThread(listener.accept()).start(); // handshake
			}
		} catch (Exception e) {
			System.out.println("I/O error: " + e.getMessage());
		}
		listener.close();
	}
}

class Contact {
	private String phoneNumber;
	private String name;

	public Contact(String n, String num) {
		this.name = n;
		this.phoneNumber = num;
	}

	public String getName() {
		return this.name;
	}

	public String getNumber() {
		return this.phoneNumber;
	}
}

class ClientThread extends Thread {
	Socket socket;

	// constructor
	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	// implement the run method
	public void run() {
		handleConnection(socket);
	}

	public void handleConnection(Socket socket) {
		List<Contact> contacts = new ArrayList<Contact>();    //array to store contacts

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			Boolean quit = false;

			out.println("Return Code: 100 - OK");     //confirmation of connection

			String client_word = in.readLine();
			System.out.println("Client connected and said " + client_word);
			String[] commands = null;

			do {
				out.println("Enter STORE, GET, REMOVE, or EXIT: ");
				System.out.println("Waiting for client response for menu...");
				client_word = in.readLine();          
				client_word = client_word.toUpperCase(); 
				switch (client_word) {
				case "STORE":
					System.out.println("Request came for storing a contact...");
					out.println("Enter the Name followed by Number to store: ");
					commands = in.readLine().split("\\s+");
					contacts.add(new Contact(commands[0], commands[1]));  //adding contact to array
               //out.println("Return Code: 100 - OK");
					break;
				case "GET":
					boolean found = false;
					out.println("Enter the Name to retrieve: ");
					commands = in.readLine().split("\\s+");
					for (Contact c : contacts) {
						if (c.getName().equals(commands[0])) {
							out.println("Return Code: 200 - Phone: " + c.getNumber());
							found = true;
							break;
						}
					}
					if(!found) {
						out.println("Return Code: 300 - Name not found in directory");	
					}
					
					break;
				case "REMOVE":
					System.out.println("Request came for removing a contact...");
					out.println("Enter the Name to delete: ");
					commands = in.readLine().split("\\s+");
					boolean removed = false;
					for (Contact c : contacts) {
						if (c.getName().equals(commands[0])) {
							out.println("Return Code: 100 - OK");
							contacts.remove(c);
							removed = true;
							break;
						}
					}
					if(!removed) {
						out.println("Return Code: 300 - Name not found in directory");
					}
					break;
				case "EXIT":
					out.println("Return Code: 100 - OK");
					quit = true;
					break;
				default:
					out.println("Return Code: 400 - Bad Request");
					break;
				}
			} while (!quit);
			in.close();
		} catch (IOException e) {
		}
	}
}
