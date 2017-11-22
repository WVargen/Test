package testJDBC;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
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
        //name = "句型_小韦.xlsx";
        //ExcelUtils.ReadFromFile(path+name);
        while(rSet.next()){
        	 cUnit_test = new chinese_unit_test(rSet.getInt(1),rSet.getString(2),rSet.getInt(3),rSet.getString(4),
        				rSet.getString(5),rSet.getString(6),rSet.getString(7),rSet.getString(8),
        				rSet.getInt(9),rSet.getString(10),rSet.getString(11),rSet.getString(12),
        				rSet.getInt(13),rSet.getInt(14),rSet.getString(15),rSet.getString(16),
        				rSet.getString(17));
        	 
        	 Class<? extends chinese_unit_test> clazz = cUnit_test.getClass();
        	 Field[] fields = clazz.getDeclaredFields();
        	 boolean editable = true;
        	 for (int i = 0; i < fields.length-1; i++) {
        		 editable = fields[i].getAnnotation(MyAnnotation.class).editable();
        		// System.out.println(editable);
        		 if (editable) {
        			// System.out.println(fields[i].getAnnotation(MyAnnotation.class).name());
				}          
             }
        	 
        	 name_temp = name;
        	 name = cUnit_test.get_id() + "_" + cUnit_test.getCourse() + "_"
        			 + cUnit_test.getBookid() + ".xlsx";
        	     	 
        	 String read = Utils.ParseJson(rSet.getString(10));
        	 String cizu = Utils.ParseJson(rSet.getString(11));
        	 String chengyu = Utils.ParseJson(rSet.getString(15));
        	 
        	 cUnit_test.setRead(read);
        	 cUnit_test.setCizu(cizu);
        	 cUnit_test.setExt_chengyu(chengyu);
        	 
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

        System.out.println("Output successful!");
    }
}
