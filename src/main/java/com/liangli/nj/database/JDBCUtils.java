package com.liangli.nj.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liangli.nj.log.FatalLog;
import com.liangli.nj.log.SqlLog;
import com.liangli.nj.utils.Definition;


public class JDBCUtils implements StorageManagerDef{
	
	public static Connection getConn() {
		Connection conn = null;
		
		try {
			Class.forName(Definition.dbCconfig.getProperty("jdbc.driverClassName")); //classLoader,加载对应驱动
			conn = DriverManager.getConnection(Definition.dbCconfig.getProperty("jdbc.url")
					, Definition.dbCconfig.getProperty("jdbc.username")
					, Definition.dbCconfig.getProperty("jdbc.password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static <T> List<T> querySql(String sql, Class<T> cls) throws Exception{
		Connection connection = getConn();
		List<T> list = new ArrayList<T>();   
	    PreparedStatement pstmt = connection.prepareStatement(sql);   
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

	@Override
	public boolean executeSQL(String sql, Object[] selectionArgs) {

		Connection conn = getConn();
		PreparedStatement pstmt = null;
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally
	    {
	    	if (pstmt != null)
	    	{
	    		try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	if (conn != null)
	    	{
	    		try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    
		return false;
	}

	@Override
	public List<Map<String, String>> executeSQLForMapList(String sql, Object[] selectionArgs) {

		Connection conn = getConn();
		PreparedStatement preState = null;
		ResultSet rs = null;
		try
		{
			if (Definition.printSqlInConsol)
			{
				SqlLog.printConsol("[BaseStorage] query ->" + sql);
			}
			
			preState = conn.prepareStatement(sql);
			rs = preState.executeQuery();
			int col = rs.getMetaData().getColumnCount();
			Map<Integer, String> columnNames = new HashMap<>();
			Map<Integer, Integer> columnTypes = new HashMap<>();
			Map<Integer, String> columnTypeNames = new HashMap<>();
			
			for (int i = 1; i <= col; i++) {
				
				columnNames.put(i, rs.getMetaData().getColumnName(i));
				columnTypes.put(i, rs.getMetaData().getColumnType(i));
				columnTypeNames.put(i, rs.getMetaData().getColumnTypeName(i));
	        }

			List<Map<String, String>> result = new ArrayList<>();
			
	        while (rs.next()) 
	        {	        	
	        	Map<String, String> map = new HashMap<>();
	        	
	        	for (int i = 1; i <= col; i ++)
	        	{
	        		String columnName = columnNames.get(i);
	        		Object columnValue = null;
	        		int type = columnTypes.get(i);
	        		
	        		switch (type)
	        		{
	        			case -1://VARCHAR
	        				columnValue = rs.getString(i);
	        				break;
	        			case 3://demical
	        				columnValue = rs.getString(i);
	        				break;
		        		case 12:
		        			columnValue = rs.getString(i);
		        			break;
		        		case -5:
		        			columnValue = rs.getLong(i);
		        			break;
		        		case 4:
		        			columnValue = rs.getInt(i);
		        			break;
		        		default:
		        			columnValue = rs.getString(i);
		        			if(columnValue != null)
		        			{
		        				FatalLog.log("unknow column types BaseStorage->find" + columnTypes.get(i) + "/" + columnTypeNames.get(i) + "/" + columnValue);
		        			}
		        			break;
	        		}
	        		
	        		if (columnValue != null
	        				&& columnName != null)
	        		{
	        			map.put(columnName, String.valueOf(columnValue));
	        		}
	        	}
	        	
	        	result.add(map);
	        }
	        
	        return result;
		}
		catch (Exception e)
		{
			FatalLog.log(sql);
			FatalLog.log(e);
		}
		finally {
			if (rs != null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					FatalLog.log(e);
				}
			}
			
			if (preState != null)
			{
				try {
					preState.close();
				} catch (SQLException e) {
					FatalLog.log(e);
				}
			}
		}
		return new ArrayList<>();
	}

	@Override
	public void checkMaxAllowedPacket() {
		// TODO Auto-generated method stub
		
	}

}