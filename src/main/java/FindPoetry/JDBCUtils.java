package FindPoetry;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JDBCUtils{
	private static final String username = "root";
	private static final String password = "123456";
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/test";
	
	public static Connection getConn() {
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
	public <T> List<T> querySql(String sql, Class<T> cls){
		Connection connection = getConn();
		List<T> list = new ArrayList<T>();  
	    int index = 1;  
	    PreparedStatement pstmt = connection.prepareStatement(sql);  
	    	if(params != null && !params.isEmpty()){  
	    		for(int i = 0; i<params.size(); i++){  
	    		pstmt.setObject(index++, params.get(i));  
	    	}  
	    }  
	    ResultSet resultSet = pstmt.executeQuery();  
	    ResultSetMetaData metaData  = resultSet.getMetaData();  
	    int cols_len = metaData.getColumnCount();  
	    while(resultSet.next()){  
	    	//通过反射机制创建一个实例  
	    	T resultObject = cls.newInstance();  
	    	for(int i = 0; i<cols_len; i++){  
	    		String cols_name = metaData.getColumnName(i+1);  
	    		Object cols_value = resultSet.getObject(cols_name);  
	    		if(cols_value == null){  
	    			cols_value = "";  
	    		}  
	    		Field field = cls.getDeclaredField(cols_name);  
	    		field.setAccessible(true); //打开javabean的访问权限  
	    		field.set(resultObject, cols_value);  
	    	}  
	    	list.add(resultObject);  
	    }  
	    return list;  
	}

}