package playground;

import myPackage.MyClient;
import myPackage.MyServer;

public class MyExercise6 {

	public static void main(String[] args) {
		MyServer server = null;
		MyClient client1 = null;
		try {
			
		server= new MyServer();

		client1=new MyClient("client1");
		client1.sendTextMessageToServer("Hello", server.serverConnection.getInputPort());

		Thread.sleep(1000);//wait for the messages to be exchanged
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		if(client1!=null)client1.close();
		if(server!=null)server.close();
		}
	}

	public void run(){
		
	}
	
}
