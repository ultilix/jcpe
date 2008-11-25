package cn.edu.njupt.sc;

import java.io.IOException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import cn.edu.njupt.sc.GUI.CPEFrame;
import cn.edu.njupt.sc.message.Inform;
import cn.edu.njupt.sc.message.Message;

/**
 * 
 * @author smartzxy@gmail.com
 * 
 */
public class CPECore {

	/**
	 * This method is used to demonstrate on GUI.
	 * @param args
	 * @throws IOException
	 */
	public void turnOn(CPEFrame gui) {
		// Create an instance of CPEClient.
		CPEClient client = new CPEClient();
		SOAPMessage msg;
		int status;
		{
			System.out.println("Initiating connection...");
			gui.insertState("Initiating connection...\n");
			// Step 1:send an empty HTTP Get to connect to the servlet.
			status = client.init();
		}
		while (status != 204)
			;
		gui.insertState("Connection has been established!\n");

			// Step 2:post Inform message to start a session.
			// create an Inform message.
			String requestName = this.informACS(gui, client);

			// If received InformResponse message, get to step 3.
			if (requestName.equals("InformResponse")) {
				System.out.println("Posting an empty HTTP_Post...");
				gui.insertState("Posting an empty HTTP_Post...\n");
			}

			// send an empty response to ACS by CPEClient.conn().
			msg = client.conn();
			System.out.println("Processing Request...");
			gui.insertState("Processing Request...\n");
			// Step 3:If receive a InformResponse message, the session begins.
			if (msg == null) {
				System.out.println("There is no need to update.");
				gui.insertState("There is no need to update.\n");
			} else
				this.processRequest(gui, msg, client);
	}
	public void turnOn(){
		// Create an instance of CPEClient.
		CPEClient client = new CPEClient();
		SOAPMessage msg;
		int status;
		{
			System.out.println("Initiating connection...");
			// Step 1:send an empty HTTP Get to connect to the servlet.
			status = client.init();
		}
		while (status != 204)
			;

		do {
			// Step 2:post Inform message to start a session.
			// create an Inform message.
			String requestName = this.informACS(client);

			// If received InformResponse message, get to step 3.
			if (requestName.equals("InformResponse")) {
				System.out.println("Posting an empty HTTP_Post...");
			}

			// send an empty response to ACS by CPEClient.conn().
			msg = client.conn();
			System.out.println("Processing Request...");
			// Step 3:If receive a InformResponse message, the session begins.
			if (msg == null) {
				System.out.println("There is no need to update.");
			} else
				this.processRequest( msg, client);
			try {
				Thread.currentThread().sleep(5 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	private String informACS(CPEFrame gui, CPEClient client) {

		// create an Inform message.
		Message inform = new Inform();
		System.out.println("Sending Inform message...");
		gui.insertState("Sending Inform message...\n");
		// send SOAP message by CPEClient.create(byte[]).
		SOAPMessage msg = client.send(inform.create());
		if (msg == null)
			// If no InformResponse message received,exit the program.
			System.exit(0);
		System.out.println("Processing InformResponse message...");
		gui.insertState("Processing InformResponse message...\n");

		String requestName = null;

		try {
			// get the name of response SOAP message.
			requestName = Message.getRequestName(msg);
			return requestName;

		} catch (SOAPException e) {
			System.err.println("There is something wrong with InformResponse");
			e.printStackTrace();
		}
		return "";
	}
	private String informACS(CPEClient client){
		
		// create an Inform message.
		Message inform = new Inform();
		System.out.println("Sending Inform message...");
		// send SOAP message by CPEClient.create(byte[]).
		SOAPMessage msg = client.send(inform.create());
		if (msg == null)
			// If no InformResponse message received,exit the program.
			System.exit(0);
		System.out.println("Processing InformResponse message...");

		String requestName = null;

		try {
			// get the name of response SOAP message.
			requestName = Message.getRequestName(msg);
			return requestName;

		} catch (SOAPException e) {
			System.err.println("There is something wrong with InformResponse");
			e.printStackTrace();
		}
		return "";
	}

	private void processRequest(CPEFrame gui, SOAPMessage msg, CPEClient client) {

		// flag to mark if there is a download currently.
		boolean transferFlag = false;
		while (msg != null || transferFlag) {
			try {

				String requestName = Message.getRequestName(msg);
				Message message = (Message) Class.forName(
						"cn.edu.njupt.sc.message." + requestName).newInstance();
				// message.parse(msg);
				message.parse(msg, gui);
				Message remsg = (Message) Class.forName(
						"cn.edu.njupt.sc.message." + requestName + "Response")
						.newInstance();
				System.out.println("Sending response message...");
				gui.insertState("Sending response message...\n");
				msg = client.send(remsg.create());
				if (msg != null || transferFlag) {
					System.out.println("Processing Request...");
					gui.insertState("Processing Request...\n");
				} else
					break;

			} catch (SOAPException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}
	private void processRequest(SOAPMessage msg, CPEClient client) {

		// flag to mark if there is a download currently.
		boolean transferFlag = false;
		while (msg != null || transferFlag) {
			try {

				String requestName = Message.getRequestName(msg);
				Message message = (Message) Class.forName(
						"cn.edu.njupt.sc.message." + requestName).newInstance();
				// message.parse(msg);
				message.parse(msg);
				Message remsg = (Message) Class.forName(
						"cn.edu.njupt.sc.message." + requestName + "Response")
						.newInstance();
				System.out.println("Sending response message...");
				msg = client.send(remsg.create());
				if (msg != null || transferFlag) {
					System.out.println("Processing Request...");
				} else
					break;

			} catch (SOAPException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

}
