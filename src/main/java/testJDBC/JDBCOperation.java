package testJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCOperation {
	public static Connection getConn(String username,String password) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		Connection conn = null;
		System.out.println("Connecting to database...");
		try {
			Class.forName(driver); //classLoader,加载对应驱动
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	 
	static int createtable(Connection conn) {
	    int i = 0;
	    String sql = "CREATE TABLE students " +
                	 "(ID INTEGER not NULL AUTO_INCREMENT, " +
                	 " Name VARCHAR(255), " + 
                	 " Sexual VARCHAR(255), " + 
                	 " Age INTEGER, " + 
                	 " PRIMARY KEY ( id ))"; 
	    PreparedStatement pstmt;
	    try {
	        pstmt = conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("Created table in given database...");
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
		
	public static ResultSet getAll(Connection conn,String sqlstring) throws SQLException {
	    String sql = sqlstring;
	    PreparedStatement pstmt;
	    pstmt = conn.prepareStatement(sql);
	    ResultSet rs = pstmt.executeQuery();  
	    return rs;
	}
	

}