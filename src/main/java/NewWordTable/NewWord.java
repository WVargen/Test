package NewWordTable;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FindPoetry.BeanUtils;
import FindPoetry.JDBCUtils;
import FindPoetry.chinese_unit_test;
import testJDBC.ExcelUtils;
import testJDBC.JDBCOperation;
import testJDBC.Utils;

public class NewWord{
	public static void main( String[] args ) throws Exception {
		String path = "C:/Users/vargen/Desktop/test/生字表";
   	 	String name = "",name_temp = "";
   	 	File file = null;
   	 	
   	 	List<chinese_unit_test> DataList = new ArrayList<chinese_unit_test>();
   	    Class<chinese_unit_test> cls = chinese_unit_test.class;
   	    String sql = "select * from chinese_unit_sh";
   	 	DataList = JDBCUtils.querySql(sql, cls);
   	    HashMap<String, Integer> wordtable = new HashMap<>();
        	
   	 	List<String []> databean = new ArrayList<String []>();
   	 	for (int i = 0; i < DataList.size(); i++) {
   	 		Map<String, String> data = BeanUtils.getFieldValueMap(DataList.get(i));
   	 		String read = Utils.ParseJson(data.get("read"));
   	 		String cizu = Utils.ParseJson(data.get("cizu"));
   	 		String chengyu = Utils.ParseJson(data.get("ext_chengyu"));
   	 		
   	 		if (read == null || read.isEmpty()){
	    		//System.out.println(cUnit_test.get_id() + cUnit_test.getBookid());
	    		read = NewWordTable.handleWordTable(wordtable,cizu);
	    	}
   	 		
   	 		data.put("read", read);
   	 		data.put("cizu", cizu);
   	 		data.put("ext_chengyu",chengyu);
   	 		
   	 		name_temp = name;
   	 		name = data.get("_id") + "_" + data.get("course") + "_" + data.get("bookid") + ".xlsx";
	   	 	
   	 		//chinese_unit_test cUnit_test = new chinese_unit_test();
   	 		List<String> recordList = new ArrayList<>();
   	 		String nameList[] = {"_id","course","bookid","name","kewenAuthor","lession","type","read","cizu","ext_chengyu"};
	   	 	if (!name.equalsIgnoreCase(name_temp)) {
	   	 		file = Utils.createFile(path,name);
	   	 		databean.clear();
	   	 		databean.add(nameList);
	   	 		System.out.println("file path:" + file);
			}
	   	 	
	   	 		for (String colume:nameList){
	   	 			recordList.add(data.get(colume));
	   	 		}
   	 		String record[] = recordList.toArray(new String[recordList.size()]);
   	 		databean.add(record);
	   	 	
	        ExcelUtils.WriteToFile(file,"Test", databean);	 
   	 	}

        System.out.println("complete！");
	}
	
}