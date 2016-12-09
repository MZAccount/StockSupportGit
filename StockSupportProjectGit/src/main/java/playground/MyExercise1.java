package playground;

import myPackage.MyJDBC_Connection;

/**
 * @author Uber
 *
 *	Am testat sa vad daca merge JDBC-ul pe o tabela standard
 */
public class MyExercise1 {

	public static void main(String[] args) {
		MyJDBC_Connection conn1=new MyJDBC_Connection();

		//conn1.executeSQL_StatementForDatabaseEmployees("DROP TABLE CUSTOMERS");
		conn1.executeSQL_StatementAny("CREATE TABLE CUSTOMERS(\r\n" + 
				"   ID   INT              NOT NULL,\r\n" + 
				"   NAME VARCHAR (20)     NOT NULL,\r\n" + 
				"   AGE  INT              NOT NULL,\r\n" + 
				"   ADDRESS  CHAR (25) ,\r\n" + 
				"   SALARY   DECIMAL (18, 2),       \r\n" + 
				"   PRIMARY KEY (ID)\r\n" + 
				")");
		System.out.println(conn1.executeSQL_StatementAny("INSERT INTO CUSTOMERS \r\n" + 
				"VALUES (7, 'Muffy', 24, 'Indore', 10000.00 )"));//String s = rs.getString(1);
		System.out.println(conn1.executeSQL_StatementAny("Select * from CUSTOMERS"));//String s = rs.getString(1);
		conn1.executeSQL_StatementAny("DROP TABLE CUSTOMERS");
		

	}

}
