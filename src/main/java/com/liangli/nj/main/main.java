package com.liangli.nj.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.liangli.nj.bean.FileBean;
import com.liangli.nj.bean.chinese_unit;
import com.liangli.nj.database.DatabaseAccessor;
import com.liangli.nj.table.NewWordTable;
import com.liangli.nj.utils.Definition;
import com.liangli.nj.utils.ExcelUtils;
import com.liangli.nj.utils.FileUtils;
import com.liangli.nj.utils.Strings;
import com.liangli.nj.utils.Utils;
import com.liangli.nj.utils.WordUtil;

import testRec.testWordMatch;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//导出数据库数据，同时生成生字
		try {
			exportChineseBooks2ExcelAndGenerateNewwords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//word抓取程序
//		fetchEnglishGrammarFromWordFile();		
	}
	
	private static void exportChineseBooks2ExcelAndGenerateNewwords() throws Exception
	{
		String path = Definition.getClassPath() + "/NewWordTable/generatefiles/";
   	 	String filename = "", lastFilename = "";
   	 	File file = null;
   	 	
   	 	Definition.printSqlInConsol = true;   	 	
   	 	List<chinese_unit> dataList = DatabaseAccessor.get().getSelect()
   	 			.select(chinese_unit.class)
   	 			.where("course=?", "yw沪教版小学")
   	 			.orderBy("bookid asc")
   	 			.find(chinese_unit.class);
   	 	
   	    HashMap<String, Integer> wordtable = new HashMap<>();
        	
   	 	List<String[]> databean = new ArrayList<String[]>();
   	 	
   	 	for (int i = 0; i < dataList.size(); i++) 
   	 	{
   	 		chinese_unit data = dataList.get(i);
   	 		String read = Utils.ParseJson(data.getRead());
   	 		String cizu = Utils.ParseJson(data.getCizu());
   	 		String chengyu = Utils.ParseJson(data.getExt_chengyu());
   	 		
   	 		if (Strings.isEmpty(read)){
	    		read = NewWordTable.handleWordTable(wordtable,cizu);
	    	}
   	 		
   	 		lastFilename = filename;
   	 		filename = "生字表" + data.get_id() + "_" + data.getCourse() + "_" + data.getBookid() + ".xlsx";
	   	 	
   	 		String nameList[] = {"_id","course","bookid","单元", "序号", "课文名","作者", "type","识字表","写字表","词组","成语"};
	   	 	
   	 		if (!filename.equalsIgnoreCase(lastFilename)) {
	   	 		file = Utils.createFile(path, filename);
	   	 		databean.clear();
	   	 		databean.add(nameList);
	   	 		System.out.println("file path:" + file);
			}
	   	 	
   	 		databean.add(new String[]{
	   	 			data.get_id() + ""
	   	 			, data.getCourse()
	   	 			, data.getBookid() + ""
	   	 			, data.getUnit()
	   	 			, data.getLession()
	   	 			, data.getName()
	   	 			, data.getKewenAuthor()
	   	 			, data.getType() + ""
	   	 			, read
	   	 			, ""
	   	 			, cizu
	   	 			, chengyu
	   	 	});
	   	 	
	        ExcelUtils.WriteToFile(file,"Test", databean);	 
   	 	}

        System.out.println("complete！");
	}
	
	//Map<String, QuestionBean>
	private static void fetchEnglishGrammarFromWordFile()
	{
		//文件生成在target/classes/testRect目录下的
    	String scanFolder = Definition.getClassPath() + "/testRec";
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
