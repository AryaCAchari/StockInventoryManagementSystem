package application.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnectionSingleton {
	
	private static MysqlConnectionSingleton mcSingle = null;
	private static Connection con = null;
	
	private MysqlConnectionSingleton() {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system?allowPublicKeyRetrieval=true&useSSL=false", "root", "root");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() {
		
		return con;
	}
	
	public static synchronized MysqlConnectionSingleton dbConnectionblock() {
		
		if(mcSingle == null) {
			
			mcSingle = new MysqlConnectionSingleton();
			
		}
		return mcSingle;
		
	}
}
