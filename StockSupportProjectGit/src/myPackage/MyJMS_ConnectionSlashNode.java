package myPackage;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class MyJMS_ConnectionSlashNode  {
	
	
	
	
	private Session serverSession = null;
	//private enum State{WAITING_FOR_A_REPLY,RUNNING};
	//State state=State.RUNNING;


	Connection thisConnection = null;
	
	//List<Closeable> unclosedObjects=new ArrayList<Closeable>();
	
	
	
	String brokerURL;
	String clientID;
	
	
	
	/**
	 * @param brokerURL	Network to which it shall connect.
	 * @param clientID	Needs to be unique to the service.
	 * @param node 
	 * @throws Exception 
	 * @throws URISyntaxException 
	 */
	public MyJMS_ConnectionSlashNode(String brokerURL,String clientID, MessageListener node) throws URISyntaxException, Exception {
		this.brokerURL=brokerURL;
		this.clientID=clientID;
		setupConnection(node);
	}
	
	
	//List<MessageProducer> listMsgProdOutputDestinations = new ArrayList<MessageProducer>();
	
	
//	/**
//	 * Connects to a node and adds producers and consumers both of them.
//	 * Note:	Sadly i can't find a simple way to include sender in messages.
//	 */
	
	public boolean sendTextMessageToDestination(String msgText,Destination msgDest){
		MessageProducer msgTempProd = null;
		try {
			msgTempProd=this.serverSession.createProducer(msgDest);
			
			
			TextMessage msg=this.serverSession.createTextMessage(msgText);
			
			msg.setJMSReplyTo(this.getInputPort());
			
			msgTempProd.send(msg);
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			if(msgTempProd!=null)
				try {
					msgTempProd.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
		}
		return true;
	}
	
	
	
	public Destination getInputPort(){
		return topicPortAllInputs;
	}
	
	
	Topic topicPortAllInputs;
	MessageConsumer msgConsPortAllInputs;
	
	void setupConnection(MessageListener node) throws URISyntaxException, Exception {
		
		
		
		
			//							CONECTION_FACTORY------------------------------------------
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"tcp://localhost:61616");
			//										CONNECTION-------------------------------------
			thisConnection = connectionFactory.createConnection();
			thisConnection.setClientID(clientID);
			serverSession = thisConnection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			
			topicPortAllInputs=serverSession.createTemporaryTopic();//do i need to close this?
			msgConsPortAllInputs=serverSession.createConsumer(topicPortAllInputs);
			msgConsPortAllInputs.setMessageListener(node);
			
			//replyProducer = serverSession.createProducer(null);
			//Topic requestDestination = serverSession.createTemporaryTopic();//createTopic("SomeTopic");
			
			
			thisConnection.start();

		
	}
	
	
	
	public void close() {

		try {
			if(msgConsPortAllInputs!=null){
				msgConsPortAllInputs.close();}
			
		if(serverSession!=null){serverSession.close();}
	
		if (thisConnection != null) {
			thisConnection.close();
		}
		} catch (JMSException e) {
			// TODO: handle exception
		}
	}
	
	

}
