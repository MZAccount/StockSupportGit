package playground;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import myPackage.MyClient;
import myPackage.MyServer;

/**
 * @author Uber
 *
 *	Am incercat sa modific functia de trimis mesaje, dar am uitat ca serverul e dispatcher, refac in ex4
 */
public class MyExercise3 implements MessageListener {
	private Session serverSession;
	private MessageProducer replyProducer;
	private enum State{WAITING_FOR_A_REPLY,RUNNING};
	State state=State.RUNNING;

	public static void main(String[] args) throws URISyntaxException, Exception {
		MyExercise3 jmsTopicExample = new MyExercise3();
		MessageListener client=new MyClient("client");
		
		MyServer server=new MyServer();
		
		server.customSetup1();
		
		
		
		jmsTopicExample.myMessageAndWaitUsingTopics("Client: Important Query",server,client);
	}
	
	
	public static void old_main(String[] args) throws URISyntaxException, Exception {
		MyExercise3 jmsTopicExample = new MyExercise3();
		MessageListener server=jmsTopicExample,client=new MyClient("client");
		jmsTopicExample.myMessageAndWaitUsingTopics("Client: Important Query",server,client);
	}
	
	
	
	
	
	/**	This method sends a text message to the second listener and then waits for a response.
	 * @param myserver
	 * @param client1
	 * @throws URISyntaxException
	 * @throws Exception
	 */
	public void myMessageAndWaitUsingTopics(String textMessageContentFromServerToClient,MessageListener myserver,MessageListener client1) throws URISyntaxException, Exception {
		//						BROKER-------------------------------------------------------------
		BrokerService broker = BrokerFactory.createBroker(new URI(
				"broker:(tcp://localhost:61616)"));
		broker.start();
		Connection serverConnection = null;
		Connection clientConnection = null;
		try {
			//							CONECTION_FACTORY------------------------------------------
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"tcp://localhost:61616");
			//										CONNECTION-------------------------------------
			serverConnection = connectionFactory.createConnection();
			serverConnection.setClientID("serverTempTopic");
			serverSession = serverConnection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			replyProducer = serverSession.createProducer(null);
			Topic requestDestination = serverSession.createTemporaryTopic();//createTopic("SomeTopic");
			
			
			
			
			//Server is listening for queries
			final MessageConsumer requestConsumer = serverSession
					.createConsumer(requestDestination);
			requestConsumer.setMessageListener(myserver);
			serverConnection.start();

			// Client sends a query to topic 'SomeTopic'
			clientConnection = connectionFactory.createConnection();
	        clientConnection.setClientID("clientTempTopic");
	        Session clientSession = clientConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        
	        
	        Destination replyDestination = clientSession.createTemporaryTopic();
	        MessageConsumer replyConsumer_ClientConsumer = clientSession.createConsumer(replyDestination);
//	    	Prepare client for response; make him listen for a response
	        replyConsumer_ClientConsumer.setMessageListener(client1);
	        
	        
	        clientConnection.start();
	        
	        
	        

	        MessageProducer requestProducer_ClientProducer = clientSession.createProducer(requestDestination);
	        //Create a client listener on the temporary topic
	        

	        
			TextMessage requestMessage = clientSession.createTextMessage(textMessageContentFromServerToClient);
	        //Server is going to send the reply to the temporary topic
	        requestMessage.setJMSReplyTo(replyDestination);
	        
	        
	        
	        //	Send message: client->server
	        requestProducer_ClientProducer.send(requestMessage);

	        System.out.println("Sent request " + requestMessage.toString());

	        
	       

	        //	Try to wait for it to respond???
	        {
	        	final long timeoutMilis=5000;
		        state=State.WAITING_FOR_A_REPLY;
		         for(int i=0;i<timeoutMilis/10;i++)
		        	{Thread.sleep(10);
		        	if(state==State.WAITING_FOR_A_REPLY)
		        		break;
		        	}
		         state=State.RUNNING;
		     }
	        
//	        
//	        
//	        //Read the answer from temporary queue.
//	        Message msg = replyConsumer_ClientConsumer.receive(5000);
//	        TextMessage replyMessage = (TextMessage)msg;
//            System.out.println("Received reply " + replyMessage.toString());
//            System.out.println("Received answer: " + replyMessage.getText());
            
	        replyConsumer_ClientConsumer.close();
	        
			clientSession.close();
			serverSession.close();
		} finally {
			if (clientConnection != null) {
				clientConnection.close();
			}
			if (serverConnection != null) {
				serverConnection.close();
			}
			broker.stop();
		}
	}

	//Server receives a query and sends reply to temporary topic set in JMSReplyTo
	public void onMessage(Message message) {
		 try {

				if(state==State.WAITING_FOR_A_REPLY)
					state=State.RUNNING;
				
	            TextMessage requestMessage = (TextMessage)message;

	            System.out.println("Received request." + requestMessage.toString());

	            Destination replyDestination = requestMessage.getJMSReplyTo();
	            TextMessage replyMessage = serverSession.createTextMessage("Server: This is my answer to " + requestMessage.getText());

	            replyMessage.setJMSCorrelationID(requestMessage.getJMSMessageID());

	            replyProducer = serverSession.createProducer(replyDestination);
                replyProducer.send(replyMessage);
                
                System.out.println("Sent reply.");
                System.out.println(replyMessage.toString());
	        } catch (JMSException e) {
	        	System.out.println(e);
	        }
	}
}
