package myPackage;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class MyJMS_Connection  {
	
	
	
	
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
	 * @throws Exception 
	 * @throws URISyntaxException 
	 */
	public MyJMS_Connection(String brokerURL,String clientID) throws URISyntaxException, Exception {
		this.brokerURL=brokerURL;
		this.clientID=clientID;
		setupConnection();
	}
	
	
	
	
	void setupConnection() throws URISyntaxException, Exception {
		
		
		
		
			//							CONECTION_FACTORY------------------------------------------
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"tcp://localhost:61616");
			//										CONNECTION-------------------------------------
			thisConnection = connectionFactory.createConnection();
			thisConnection.setClientID(clientID);
			serverSession = thisConnection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			//replyProducer = serverSession.createProducer(null);
			//Topic requestDestination = serverSession.createTemporaryTopic();//createTopic("SomeTopic");
			
			
			thisConnection.start();

		
	}
	
	
	
	public void close() {

		try {
			
		if(serverSession!=null){serverSession.close();}
	
		if (thisConnection != null) {
			thisConnection.close();
		}
		} catch (JMSException e) {
			// TODO: handle exception
		}
	}
	
	

}
