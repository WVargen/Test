package NewWordTable;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import testJDBC.ExcelUtils;
import testJDBC.JDBCOperation;
import testJDBC.MyAnnotation;
import testJDBC.Utils;
import testJDBC.chinese_unit_test;

public class NewWord{
	public static void main( String[] args ) throws SQLException {
		
		List<String []> databean = new ArrayList<String []>();
		
		String username = "root";
    	String password = "123456";
   	 	String path = "C:/Users/hp/Desktop/test/";
   	 	String name = "";
   	 	String name_temp = "";
   	 	File file = null;
    	Connection conn = JDBCOperation.getConn(username,password);
        ResultSet rSet = JDBCOperation.getAll(conn);
        chinese_unit_test cUnit_test = null;

        while(rSet.next()){
        	 cUnit_test = new chinese_unit_test(rSet.getInt(1),rSet.getString(2),rSet.getInt(3),rSet.getString(4),
        				rSet.getString(5),rSet.getString(6),rSet.getString(7),rSet.getString(8),
        				rSet.getInt(9),rSet.getString(10),rSet.getString(11),rSet.getString(12),
        				rSet.getInt(13),rSet.getInt(14),rSet.getString(15),rSet.getString(16),
        				rSet.getString(17));
        	 String title = "";
        	 String read = "";
        	 String cizu = "";
        	 String chengyu = "";

        	 Class<? extends chinese_unit_test> clazz = cUnit_test.getClass();
        	 Field[] fields = clazz.getDeclaredFields();

        	 boolean editable = false;
        	 for (int i = 0; i < fields.length-1; i++) {
        		 editable = fields[i].getAnnotation(MyAnnotation.class).editable();
        		 if (editable) {
        			 title = fields[i].getAnnotation(MyAnnotation.class).name();
        			 if (title.equalsIgnoreCase("内容") ) {
        				 read = Utils.ParseJson(rSet.getString(10));
        				 String string = NewWordTable.handleWordTable(read);
        				 //System.out.println(string);
        				 cUnit_test.setRead(string.toString());
					}
        			 else if (title.equalsIgnoreCase("词组")) {
        				 cizu = Utils.ParseJson(rSet.getString(11));
        				 cUnit_test.setCizu(cizu);
					}else if (title.equalsIgnoreCase("成语")) {
						chengyu = Utils.ParseJson(rSet.getString(15));
						cUnit_test.setExt_chengyu(chengyu);
					}
        		 }          
             }
        	 
        	 name_temp = name;
        	 name = cUnit_test.get_id() + "_" + cUnit_test.getCourse() + "_"
        			 + cUnit_test.getBookid() + ".xls";
        	 
        	 String[] data = cUnit_test.getChinese_unit_string();     	
        	 databean.add(data);
        	 
        	 if (!name.equalsIgnoreCase(name_temp)) {
        		 file = Utils.createFile(path,name);
        		 databean.clear();
        		 databean.add(chinese_unit_test.getTitle());
        		 System.out.println("file path:" + file);
			}
        	 ExcelUtils.WriteToFile(file, databean);	 
        }
        System.out.println("complete！");
	}
	
}