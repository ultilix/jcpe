package cn.edu.njupt.sc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import cn.edu.njupt.sc.data.DataReader;
import cn.edu.njupt.sc.data.TreeDataReader;

//import cn.edu.njupt.sc.GUI.CPEFrame;

public class CPEClient {
	static {
		// URL = "http://localhost:8080/ACS/ACSServlet";
		// URL="http://10.20.66.2:8080/ACS/ACSServlet";
		// Create an instance of HttpClient.
		client = new HttpClient();
	}

	// public CPEClient(CPEFrame gui) {
	// this.gui = gui;
	// }
	public CPEClient() {

		DataReader reader = new TreeDataReader();
//		DataReader reader = new TreePullReader();
		reader.setSource("Data.xml");
		this.URL = reader.read("InternetGatewayDevice.ManagementServer.URL");
		this.username = reader
				.read("InternetGatewayDevice.ManagementServer.Username");
		this.password = reader
				.read("InternetGatewayDevice.ManagementServer.Password");
	}

	public int init() {

		// Set authentication.
		client.getState().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, 8080, AuthScope.ANY_REALM),
				new UsernamePasswordCredentials(username, password));
		// Create a method instance.
		GetMethod get = new GetMethod(URL);
		// Tell the GET method to automatically handle authentication.
		get.setDoAuthentication(true);
		// Provide custom retry handler .
		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		// Set the length of HTTP message-body to zero.
		get.setRequestHeader("Content-Length", "0");
		int status;

		// Execute the method.
		try {
			status = client.executeMethod(get);
			redirect(status, get);
			Header[] header = get.getResponseHeaders();
			for (Header head : header) {
				System.out.println(head.getName() + ": " + head.getValue());
				// gui.insertDate(head.getName() + ": " + head.getValue() +
				// "\n");
				// gui.insertDate("**********************************************");
			}
			// gui.insertDate("**************************************************\n");
			return status;
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			get.releaseConnection();
		}
		return -1;
	}

	public SOAPMessage conn() {

		// Set authentication.
		client.getState().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, 8080, AuthScope.ANY_REALM),
				new UsernamePasswordCredentials("username", "password"));
		// Create a method instance.
		PostMethod post = new PostMethod(URL);
		// Tell the GET method to automatically handle authentication.
		post.setDoAuthentication(true);
		// Provide custom retry handler .
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		int status;
		SOAPMessage message = null;

		try {
			// Execute the method.
			status = client.executeMethod(post);
			redirect(status, post);
			// Get InputStream from the response.
			InputStream in;
			if (status == HttpStatus.SC_OK) {
				in = post.getResponseBodyAsStream();

				try {
					message = MessageFactory.newInstance().createMessage(null,
							in);
					message.writeTo(System.out);

				} catch (SOAPException e) {
					System.err.println("error in client!");
					e.printStackTrace();
				}
				return message;

			} else if (status == HttpStatus.SC_NO_CONTENT) {
				Header[] header = post.getResponseHeaders();
				for (Header head : header) {
					System.out.println(head.getName() + ": " + head.getValue());
					// gui.insertDate(head.getName() + ": " + head.getValue()
					// + "\n");
				}
				// gui.insertDate("**************************************************\n");
				return null;
			} else {
				// in = conn();
				return null;
			}
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			post.releaseConnection();
		}
		return null;
	}

	public SOAPMessage send(byte[] msg) {

		// Prepare HTTP post.
		PostMethod post = new PostMethod(URL);
		InputStream i = new ByteArrayInputStream(msg);
		try {
			SOAPMessage message = MessageFactory.newInstance().createMessage(
					null, i);
			message.writeTo(System.out);
			System.out.println("");
			// gui.insertDate(message.toString() + "\n");
			i.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SOAPException e1) {
			e1.printStackTrace();
		}
		// Add SOAP message.
		RequestEntity entity = new ByteArrayRequestEntity(msg, "text/xml");
		post.setRequestEntity(entity);
		// consult documentation for your web service.
		post.setRequestHeader("SOAPAction", null);
		int status;
		SOAPMessage message = null;

		try {
			status = client.executeMethod(post);
			redirect(status, post);
			InputStream in;
			// Get InputStream from the response.
			if (status == HttpStatus.SC_OK) {
				in = post.getResponseBodyAsStream();
				System.out.println("receiving message...");
				// gui.insertState("receiving message...\n");
				// System.out.println(post.getResponseBodyAsString());
				Header[] header = post.getResponseHeaders();
				for (Header head : header) {
					System.out.println(head.getName() + ": " + head.getValue());
					// gui.insertDate(head.getName() + ": " + head.getValue()
					// + "\n");
				}
				// gui.insertDate("**************************************************\n");
				try {
					message = MessageFactory.newInstance().createMessage(null,
							in);
					message.writeTo(System.out);
				} catch (SOAPException e) {
					e.printStackTrace();
				}
				return message;
			} else if (status == HttpStatus.SC_NO_CONTENT) {
				System.out.println("Nothing more received!");
				// gui.insertState("Nothing more received!\n");
				Header[] header = post.getResponseHeaders();
				for (Header head : header) {
					System.out.println(head.getName() + ": " + head.getValue());
					// gui.insertDate(head.getName() + ": " + head.getValue()
					// + "\n");
				}
				// gui.insertDate("**************************************************\n");
				return null;
			} else {
				Header[] header = post.getResponseHeaders();
				for (Header head : header) {
					System.out.println(head.getName() + ": " + head.getValue());
					// gui.insertDate(head.getName() + ": " + head.getValue()
					// + "\n");
				}
				// gui.insertDate("**************************************************\n");
				System.out.println(status);
				// in = send(msg);
				return null;
			}
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release current connection to the connection pool.
			post.releaseConnection();
		}
		return null;

	}

	private void redirect(int status, HttpMethod method) {
		if (status == HttpStatus.SC_TEMPORARY_REDIRECT
				|| status == HttpStatus.SC_MOVED_TEMPORARILY)
			URL = method.getResponseHeader("NewURL").getValue();
	}

	// private CPEFrame gui;
	private String username;
	private String password;
	public String URL = "http://localhost:8080/ACS/ACSServlet";
	public static HttpClient client;

}
