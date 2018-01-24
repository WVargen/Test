/**
 * 
 */
/**
 * @author vargen
 *
 */
package com.liangli.nj.testRec;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.bean.FileBean;
import com.liangli.nj.utils.*;


public class RegularExpression
{
    public static void main( String[] args ){
    	
    	//文件生成在target/classes/testRect目录下的
    	String scanFolder = RegularExpression.class.getClassLoader().getResource("testRec").getPath();
    	File folder = new File(scanFolder);
    	
    	List<FileBean> tasks = new ArrayList<>();
    	List<FileBean> matchSentenceTasks = new ArrayList<>();
    	for (String filename : folder.list())
    	{
    		if (FileUtils.checkFileFormat(filename, ".docx"))
    		{
    			String inputPath = scanFolder + "/" + filename;
    			String outputPath = scanFolder + "/" + FileUtils.cutFileFormat(filename, ".docx") + ".xlsx";
    			tasks.add(new FileBean(inputPath, outputPath));
    		}else if (FileUtils.checkFileFormat(filename, ".xlsx")) {
    			 if (filename.equals("句型.xlsx")) {
    				 String inputPath = scanFolder + "/" + filename;
    	    			String outputPath = scanFolder + "/" + FileUtils.cutFileFormat(filename, ".xlsx")+ "match" + ".xlsx";
    	    			matchSentenceTasks.add(new FileBean(inputPath, outputPath));
				}	
    		}
    	}
    	
    	for (FileBean task : tasks)
    	{
	    	//word文档部分
	    	String doc_read = WordUtil.readDataDocx(task.getInputPath());
	    	
	    	List<String []> doc_data = new ArrayList<>();
	    	String [] title = {"uuid","type","question","a","b","c","d","answer","explain"};
	    	doc_data.add(title);
	    	doc_data.addAll(testWordMatch.matchWord(doc_read));
//	    	//System.out.println(doc_data);
	    	ExcelUtils.WriteToFile(new File(task.getOutputPath()),"1", doc_data);
	    	
	    	
	    	System.out.println("Match Word complete！");
    	}
    	for (FileBean task : matchSentenceTasks) {
			//句型匹配部分
    		String[][] xls_read = ExcelUtils.ReadFromFile(task.getInputPath());
	    	
    		List<String []> xls_data = new ArrayList<>();
        	String [] title = {"_id","course","bookid","unitid","unitidorder","name",
        			"uuid","type","question","a","b","c","d","answer","explain","grammerid"};
        	xls_data.add(title);
        	xls_data.addAll(testXlsMatch.matchxls(xls_read));
        	ExcelUtils.WriteToFile(new File(task.getOutputPath()),"1", xls_data);	
	    	System.out.println("Match sentences complete！");
		}
    }
}