package playground;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import myPackage.MyClient;
import myPackage.MyJMS_ConnectionSlashNode;
import myPackage.MyServer;

public class MyExercise4 {

	public static void main(String[] args) {
		MyServer server=new MyServer();
		//server.setupJMS();
		final String brokerURL="tcp://localhost:61616";
		
		BrokerService broker;
		try {
			broker = BrokerFactory.createBroker(new URI(
					"broker:("+brokerURL+")"));
		
		broker.start();
		System.out.println("JMS started!");
		MyJMS_ConnectionSlashNode serverConnection=new MyJMS_ConnectionSlashNode(brokerURL, "server", server);
		
		//MyClient client1=new MyClient();
		
		
		
		
		
		
		
		
		
		
		
//		MessageProducer x=server.getMessageProducer();
//		
//		client1.setServerMessageProducer(x);
//		
		serverConnection.close();
		
		
		
		System.out.println("All done!");
		broker.stop();// ffs
		
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
		//broker.stop();
		//server.closeJMS();
	}

}
