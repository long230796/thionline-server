package thionline.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws Exception {
		Connection con = 
				DriverManager.getConnection(
						"jdbc:sqlserver://localhost:1433;databaseName=KIEMTRALTM;user=sa;password=administrator");
		return con;
	}
}
