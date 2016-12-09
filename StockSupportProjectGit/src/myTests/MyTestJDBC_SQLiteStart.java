package myTests;

import static org.junit.Assert.*;

import org.junit.Test;

import myPackage.MyJDBC_Connection;

public class MyTestJDBC_SQLiteStart {

	@Test
	public void testDatabaseCreationAndInsertion() {
		MyJDBC_Connection conn1 = new MyJDBC_Connection();

		// conn1.executeSQL_StatementForDatabaseEmployees("DROP TABLE
		// CUSTOMERS");

		final String tableName = "transactions";
		String rez="";

		rez+=conn1.executeSQL_StatementAny("CREATE TABLE " + tableName + "("
				+ "user_id VARCHAR (20)     NOT NULL,"
				+ "company_index VARCHAR (20)   NOT NULL,"
				+ "price    DECIMAL (18, 2),"
				+ "lastUpdate TIMESTAMP,"	//	TIMESTAMP = Stores year, month, day, hour, minute, and second values
				+ "price_difference_update    DECIMAL (18, 2),"
				+ "type VARCHAR (20)    NOT NULL,"//	Type = stock/offer_buy/offer_sell
				+ "transaction_ID VARCHAR (20)   NOT NULL ,"
				+ "PRIMARY KEY (transaction_ID)"
				+ ")");
		
		
		rez+=conn1.executeSQL_StatementAny(
				"INSERT INTO " + tableName + " \r\n" + 
		"VALUES ('user1','COLA',100.50, '2013-07-21-14:44:53',0.00,'stock','hash_abc' )");// String
		rez+=conn1.executeSQL_StatementAny(
				"INSERT INTO " + tableName + " \r\n" + 
		"VALUES ('user2','PEPSI',70.50, '2013-07-21-14:49:13',0.00,'stock','hash_cfg' )");// String
																										
		rez+=conn1.executeSQL_StatementAny("Select * from " + tableName + "");
		
		rez+=conn1.executeSQL_StatementAny("DROP TABLE " + tableName + "");

		
		
		
		final String requiredOutput ="newRows: 0\r\n" + 
				"newRows: 1\r\n" + 
				"newRows: 1\r\n" + 
				" user_id: user1\r\n" + 
				" company_index: COLA\r\n" + 
				" price: 100.5\r\n" + 
				" lastUpdate: 2013-07-21-14:44:53\r\n" + 
				" price_difference_update: 0\r\n" + 
				" type: stock\r\n" + 
				" transaction_ID: hash_abc\r\n" + 
				"\r\n" + 
				" user_id: user2\r\n" + 
				" company_index: PEPSI\r\n" + 
				" price: 70.5\r\n" + 
				" lastUpdate: 2013-07-21-14:49:13\r\n" + 
				" price_difference_update: 0\r\n" + 
				" type: stock\r\n" + 
				" transaction_ID: hash_cfg\r\n" + 
				"\r\n" + 
				"newRows: 0\r\n" ;
		
		
		System.out.println(rez);//	Make sure output is good
		
		//assertEquals(rez,requiredOutput);
		
		
		
		
	}

}
