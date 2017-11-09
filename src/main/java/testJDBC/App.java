package testJDBC;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    	List<String []> databean = new ArrayList<String []>();
    	String path = "C:/Users/vargen/Desktop";
    	String name = "chinese_unit_test.xls";
    	String username = "root";
    	String password = "123456";
    	
    	File file = Utils.createFile(path,name);
    	Connection conn = JDBCOperation.getConn(username,password);
        ResultSet rSet = JDBCOperation.getAll(conn);
        chinese_unit_test cUnit_test = null;
        int i = 0;
        databean.add(chinese_unit_test.getTitle());
        while(rSet.next()){
        	 i++;
        	 cUnit_test = new chinese_unit_test(rSet.getInt(1),rSet.getString(2),rSet.getInt(3),rSet.getString(4),
        				rSet.getString(5),rSet.getString(6),rSet.getString(7),rSet.getString(8),
        				rSet.getString(9),rSet.getInt(10),rSet.getString(11),rSet.getString(12),
        				rSet.getString(13),rSet.getInt(14),rSet.getInt(15),rSet.getString(16),
        				rSet.getString(17));
        	 String cizu = Utils.ParseJson(rSet.getString(12)).toString();
        	 String chengyu = Utils.ParseJson(rSet.getString(16)).toString();
        	 cUnit_test.setCizu(cizu);
        	 cUnit_test.setExt_chengyu(chengyu);
        	 String[] data = cUnit_test.getChinese_unit_string();
        	 System.out.println("第"+i+"行数据...");      	
        	 databean.add(data);
        }

        ExcelUtils.WriteToFile(file, databean);
        System.out.println("Output successful!");
    }
}
