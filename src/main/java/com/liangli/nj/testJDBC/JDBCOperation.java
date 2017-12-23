package com.liangli.nj.testJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.liangli.nj.bean.chinese_unit;
import com.mysql.jdbc.PreparedStatement;

class JDBCOperation {
	private static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		String username = "root";
		String password = "123456";
		Connection conn = null;
		System.out.println("Connecting to database...");
		try {
			Class.forName(driver); //classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	 
	static int createtable() {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "CREATE TABLE students " +
                	 "(ID INTEGER not NULL AUTO_INCREMENT, " +
                	 " Name VARCHAR(255), " + 
                	 " Sexual VARCHAR(255), " + 
                	 " Age INTEGER, " + 
                	 " PRIMARY KEY ( id ))"; 
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("Created table in given database...");
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	static List<chinese_unit> getAll() {
	    Connection conn = getConn();
	    String sql = "select * from chinese_unit_test";
	    PreparedStatement pstmt;
	    List<chinese_unit> databean = new ArrayList<chinese_unit>();
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            chinese_unit chi_uni = new chinese_unit();
	            chi_uni.set_id(rs.getInt(1));
	            chi_uni.setCourse(rs.getString(2));
				chi_uni.setBookid(rs.getInt(3));
		        chi_uni.setUnitid(rs.getString(4));
		        chi_uni.setUnit(rs.getString(5));
		        chi_uni.setName(rs.getString(6));
		        chi_uni.setPermission(rs.getString(7));
		        chi_uni.setKewenAuthor(rs.getString(8));
		        chi_uni.setLession(rs.getString(9));
		        chi_uni.setType(rs.getInt(10));
		        chi_uni.setRead(rs.getString(11));
		        chi_uni.setCizu(rs.getString(12));
		        chi_uni.setAudio_filename(rs.getString(13));
		        chi_uni.setDisplayOrder(rs.getInt(14));
		        chi_uni.setUnit_edition(rs.getInt(15));
		        chi_uni.setExt_chengyu(rs.getString(16));
		        chi_uni.setPermissiongroup(rs.getString(17));
		        databean.add(chi_uni);
		        //System.out.println(chi_uni.getName());
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return databean;
	}
	

}