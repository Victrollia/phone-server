//Student: Victoria Cadogan
import java.io.*;
import java.util.*;
import java.net.*;

public class PhoneClient {
	public static void main(String[] args)
	{
		String host = "localhost";
		int port = 2014;

		PhoneClientI nwClient = new PhoneClientI(host, port);
		nwClient.connect();
	}
}

class PhoneClientI {
	protected String host;
	protected int port;

   //constructor
	public PhoneClientI(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() {
		try {
			InetAddress addr = InetAddress.getByName(host);
			Socket client = new Socket(addr, port);
			handleConnection(client);
		} catch (UnknownHostException uhe) {
			System.out.println("Unknown host: " + uhe);
		} catch (IOException ioe) {
			System.out.println("IOException: " + ioe);
		}
	}

	protected void handleConnection(Socket client) throws IOException {
		String s = "";
		BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
      
      //display a confirmation of successful connection
		System.out.println("Phone Client " + "made connection to " + host + " and got " + fromServer.readLine() + "\n");
		out.println("Hi Server");
		String choice = null;
		String commands = null;
		String response = null;
		do {
			response = fromServer.readLine();   //will read the input from server
			System.out.println(response); 
			choice = kbd.readLine();
			choice = choice.toUpperCase();
			out.println(choice);
			switch (choice) {
			case "STORE":
				response = fromServer.readLine();   //establishing a read write order
				System.out.println(response);
				commands = kbd.readLine();
				out.println(commands);
				break;
			case "GET":
				response = fromServer.readLine();  
				System.out.println(response);
				commands = kbd.readLine();
				out.println(commands);
				response = fromServer.readLine();
				System.out.println(response);
				break;
			case "REMOVE":
				response = fromServer.readLine();
				System.out.println(response);
				commands = kbd.readLine();
				out.println(commands);
				response = fromServer.readLine();
				System.out.println(response);
				break;
			case "EXIT":
				response = fromServer.readLine();
				System.out.println(response);
				break;
			default:
				response = fromServer.readLine();
				System.out.println(response);
				break;
			}
		} while (!choice.equals("") && !choice.equalsIgnoreCase("EXIT"));
		client.close();
	}

}
