package playground;

import myPackage.MyJDBC_Connection;

/**
 * @author Uber
 *
 *	Am creat tabela de tranzactii si am inserat 2 randuri
 */
public class MyExercise2 {// user_id,company_index,price,lastUpdate,price_difference_update,type=stock/offer_buy/offer_sell,transaction_ID

	public static void main(String[] args) {
		MyJDBC_Connection conn1 = new MyJDBC_Connection();

		// conn1.executeSQL_StatementForDatabaseEmployees("DROP TABLE
		// CUSTOMERS");

		final String tableName = "transactions";

		System.out.println(conn1.executeSQL_StatementAny("CREATE TABLE " + tableName + "("
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
				"INSERT INTO " + tableName + " \r\n" + 
		"VALUES ('user1','COLA',100.50, '2013-07-21-14:44:53',0.00,'stock','hash_abc' )"));// String
		System.out.println(conn1.executeSQL_StatementAny(
				"INSERT INTO " + tableName + " \r\n" + 
		"VALUES ('user2','PEPSI',70.50, '2013-07-21-14:49:13',0.00,'stock','hash_cfg' )"));// String
																										
		System.out.println(conn1.executeSQL_StatementAny("Select * from " + tableName + ""));
		
		System.out.println(conn1.executeSQL_StatementAny("DROP TABLE " + tableName + ""));
		
	}

}
