package myPackage;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;



public class MyServer implements MessageListener {
	
	
	final String transactionsTableName = "transactions";
	
	//setup empty server with 2 inputs
	public void customSetup1() {

		
		MyJDBC_Connection conn1 = new MyJDBC_Connection();

		// conn1.executeSQL_StatementForDatabaseEmployees("DROP TABLE
		// CUSTOMERS");


		System.out.println(conn1.executeSQL_StatementAny("CREATE TABLE " + transactionsTableName + "("
				+ "user_id VARCHAR (20)     NOT NULL,"
				+ "company_index VARCHAR (20)   NOT NULL,"
				+ "price    DECIMAL (18, 2),"
				+ "lastUpdate TIMESTAMP,"	//	TIMESTAMP = Stores year, month, day, hour, minute, and second values
				+ "price_difference_update    DECIMAL (18, 2),"
				+ "type VARCHAR (20)    NOT NULL,"//	Type = stock/offer_buy/offer_sell
				+ "transaction_ID VARCHAR (20)   NOT NULL ,"
				+ "PRIMARY KEY (transaction_ID)"
				+ ")"));
		
		System.out.println(conn1.executeSQL_StatementAny(
				"INSERT INTO " + transactionsTableName + " \r\n" + 
		"VALUES ('user1','COLA',100.50, '2013-07-21-14:44:53',0.00,'stock','hash_abc' )"));// String
		System.out.println(conn1.executeSQL_StatementAny(
				"INSERT INTO " + transactionsTableName + " \r\n" + 
		"VALUES ('user2','PEPSI',70.50, '2013-07-21-14:49:13',0.00,'stock','hash_cfg' )"));// String
																										
		System.out.println(conn1.executeSQL_StatementAny("Select * from " + transactionsTableName + ""));
		

	}
	public void customClose1(){

		MyJDBC_Connection conn1 = new MyJDBC_Connection();
		//final String tableName = "transactions";
		System.out.println(conn1.executeSQL_StatementAny("DROP TABLE " + transactionsTableName + ""));
		
	}
	

	public void sendTextMessageToNode(String text, Destination msgDest){
		this.serverConnection.sendTextMessageToDestination(text, msgDest);
	}

		//Server receives a query and sends reply to temporary topic set in JMSReplyTo
		public void onMessage(Message message) {
			TextMessage textMsg=(TextMessage) message;
			try {
				System.out.println("Server:	Yeah someone texted me: "+textMsg.getText());
				sendTextMessageToNode("Server confirmes message",textMsg.getJMSReplyTo());
			    
				
				
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}

		
		
		

		public MyJMS_ConnectionSlashNode serverConnection;
		private BrokerService broker;
		
		public MyServer() {
			final String brokerURL="tcp://localhost:61616";
			
			//BrokerService broker;
			try {
				broker = BrokerFactory.createBroker(new URI(
						"broker:("+brokerURL+")"));
			
			broker.start();
			//System.out.println("JMS started!");
			serverConnection=new MyJMS_ConnectionSlashNode(brokerURL, "server", this);
			
			
			
			
			
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
			}
		}
		
	public void close() {

		serverConnection.close();
		try {
			broker.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
