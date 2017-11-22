package FindPoetry;

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

public class FindPoetry{
	public static void main( String[] args ) throws Exception {
		
		List<String []> databean = new ArrayList<String []>();
		
		String username = "root";
    	String password = "123456";
   	 	String path = "C:/Users/hp/Desktop/test/";
   	 	String name = "";
   	 	String name_temp = "";
   	 	File file = null;
    	Connection conn = JDBCOperation.getConn(username,password);
        ResultSet rSet = JDBCOperation.getAll(conn);
        chinese_unit_test cUnit_test = new chinese_unit_test();
        
        BeanUtils.getFieldValueMap(cUnit_test);

//        while(rSet.next()){
//        	
//            String [] value = new String[17];
//            for (int i = 0; i < value.length; i++) {
//				
//			}
//            BeanUtils.processCUnit(cUnit_test, cUnit_test, value);
////        	 name_temp = name;
////        	 name = cUnit_test.get_id() + "_" + cUnit_test.getCourse() + "_"
////        			 + cUnit_test.getBookid() + ".xlsx";
////        	 
////        	 String[] data = cUnit_test.getChinese_unit_string();     	
////        	 databean.add(data);
////        	 
////        	 if (!name.equalsIgnoreCase(name_temp)) {
////        		 file = Utils.createFile(path,name);
////        		 databean.clear();
////        		 databean.add(chinese_unit_test.getTitle());
////        		 System.out.println("file path:" + file);
////			}
////        	 ExcelUtils.WriteToFile(file, databean);	 
//        }
//        System.out.println("complete！");
	}
	
}