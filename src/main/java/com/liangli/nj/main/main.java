package com.liangli.nj.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liangli.nj.bean.*;
import com.liangli.nj.utils.*;

import NewWordTable.NewWordTable;
import testRec.RegularExpression;
import testRec.testWordMatch;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("frank aa:");
		
		//导出数据库数据，同时生成生字
		try {
//			exportChineseBooks2ExcelAndGenerateNewwords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//word抓取程序
		fetchEnglishGrammarFromWordFile();		
	}
	
	private static void exportChineseBooks2ExcelAndGenerateNewwords() throws Exception
	{
		String path = NewWordTable.class.getClassLoader().getResource("NewWordTable").getPath()+"/generatefiles/";
		//String path = "C:/Users/hp/Desktop/test/古诗+生字表/生字表";
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
	    		read = NewWordTable.handleWordTable(wordtable,cizu);
	    	}
   	 		
   	 		data.put("read", read);
   	 		data.put("cizu", cizu);
   	 		data.put("ext_chengyu",chengyu);
   	 		
   	 		name_temp = name;
   	 		name = "生字表"+data.get("_id") + "_" + data.get("course") + "_" + data.get("bookid") + ".xlsx";
	   	 	
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

        System.out.println("match complete！");
	}
	
	//Map<String, QuestionBean>
	private static void fetchEnglishGrammarFromWordFile()
	{
		//文件生成在target/classes/testRect目录下的
    	String scanFolder = RegularExpression.class.getClassLoader().getResource("testRec").getPath();
    	File folder = new File(scanFolder);
    	
    	List<FileBean> tasks = new ArrayList<>();
    	for (String filename : folder.list())
    	{
//    		if ("例子2.docx".equals(filename) == false)
//    		{
//    			continue;
//    		}
    		
    		if (FileUtils.checkFileFormat(filename, ".docx"))
    		{
    			String inputPath = scanFolder + "/" + filename;
    			String outputPath = scanFolder + "/" + FileUtils.cutFileFormat(filename, ".docx") + ".xlsx";
    			tasks.add(new FileBean(inputPath, outputPath));
    		}
    	}
    	
    	for (FileBean task : tasks)
    	{
	    	//word文档部分
	    	String doc_read = WordUtil.readDataDocx(task.getInputPath());
	    	
	    	List<String []> doc_data = new ArrayList<>();
	    	//String [] title = {"uuid","type","question","a","b","c","d","answer","explain"};
	    	//doc_data.add(title);
	    	doc_data.addAll(testWordMatch.matchWord(doc_read));
//	    	//System.out.println(doc_data);
	    	ExcelUtils.WriteToFile(new File(task.getOutputPath()),"1", doc_data);
	    	
	    	
	    	System.out.println("complete！");
    	}
	}

}
