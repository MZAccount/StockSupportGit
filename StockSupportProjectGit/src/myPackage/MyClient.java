package myPackage;

import java.net.URISyntaxException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

public class MyClient implements MessageListener {

	private static final String brokerServiceURL = "tcp://localhost:61616";


	public boolean didsomething=false;

	
	public static Destination serverDestination;
	
	
	public void onMessage(Message message) {
		//Message msg = replyConsumer_ClientConsumer.receive(5000);
        TextMessage replyMessage = (TextMessage)message;
        //System.out.println("Received reply " + replyMessage.toString());
        try {
			System.out.println("Client: Got a reply: " + replyMessage.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println("Action completed from MyClient instance.");
        didsomething=true;
	}


	private MyJMS_ConnectionSlashNode clientConnection;
	
	public MyClient(String userID) {
		try {
			clientConnection=new MyJMS_ConnectionSlashNode(brokerServiceURL, userID, this);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendTextMessageToServer(String text, Destination msgDest){
		this.clientConnection.sendTextMessageToDestination(text, msgDest);
	}

	public void close() {

		if(clientConnection!=null)clientConnection.close();
		
	}

	
	
}
