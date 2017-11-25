package NewWordTable;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FindPoetry.BeanUtils;
import testJDBC.ExcelUtils;
import testJDBC.JDBCOperation;
import testJDBC.Utils;

public class NewWord{
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
        
        //BeanUtils.getFieldValueMap(cUnit_test);
        
        Map<String, String> valueMap = new HashMap<>();
        Map<String, String> resultMap = new HashMap<>();
        HashMap<String, Integer> worldtable = new HashMap<>();
        String kString = null;
        String vString = null;
        List<String> nameList = BeanUtils.getNamelist(cUnit_test,false);
        List<String> chinameList = BeanUtils.getNamelist(cUnit_test, true);
        //System.out.println(nameList);
        while(rSet.next()){
        	
        	for(int i = 0;i<rSet.getMetaData().getColumnCount();i++){
        		kString = nameList.get(i);
        		//System.out.println(kString);
                vString = rSet.getString(i+1);
                //System.out.println(vString);
                valueMap.put(kString, vString);
            }
        	//System.out.println(valueMap);
        	
        	BeanUtils.setFieldValue(cUnit_test, valueMap);
        	String read = Utils.ParseJson(rSet.getString(10));
       	 	String cizu = Utils.ParseJson(rSet.getString(11));
       	 	
        	if (read == null || read.isEmpty()){
        		//System.out.println(cUnit_test.get_id() + cUnit_test.getBookid());
        		read = NewWordTable.handleWordTable(worldtable,cizu);
        	}
        	
       	 	String chengyu = Utils.ParseJson(rSet.getString(15));
       	 
       	 	cUnit_test.setRead(read);
       	 	cUnit_test.setCizu(cizu);
       	 	cUnit_test.setExt_chengyu(chengyu);
       	 	
        	name_temp = name;
        	name = cUnit_test.get_id() + "_" + cUnit_test.getCourse() + "_"
        			 + cUnit_test.getBookid() + ".xlsx";
        	 
        	resultMap = BeanUtils.getFieldValueMap(cUnit_test);
        	
        	List<String> v = new ArrayList<>();
        	List<String> chinamelist_edit = new ArrayList<>();
        	for (int i = 0; i < nameList.size(); i++) {
        		List<Boolean> editable = BeanUtils.getEditable(cUnit_test);
        		if (editable.get(i)) {
					chinamelist_edit.add(chinameList.get(i));
    				String k = nameList.get(i);
    				v.add(resultMap.get(k));
				}else {

				}	
			}

            databean.add(v.toArray(new String[v.size()]));
       	
        	if (!name.equalsIgnoreCase(name_temp)) {
        		file = Utils.createFile(path,name);
        		databean.clear();
        		databean.add(chinamelist_edit.toArray(new String[chinamelist_edit.size()]));
        		System.out.println("file path:" + file);
			}
        	ExcelUtils.WriteToFile(file, databean);	 
        }
        System.out.println("complete！");
	}
	
}