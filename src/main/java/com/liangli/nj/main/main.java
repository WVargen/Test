package com.liangli.nj.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liangli.nj.bean.FileBean;
import com.liangli.nj.bean.chinese_unit;
import com.liangli.nj.database.DatabaseAccessor;
import com.liangli.nj.mathmethod.MathManager;
import com.liangli.nj.table.NewWordTable;
import com.liangli.nj.testRec.KnowledgePointRecognition;
import com.liangli.nj.testRec.testWordMatch;
import com.liangli.nj.testRec.testXlsMatch;
import com.liangli.nj.utils.Definition;
import com.liangli.nj.utils.DeviceUtils;
import com.liangli.nj.utils.ExcelUtils;
import com.liangli.nj.utils.FileUtils;
import com.liangli.nj.utils.HttpUtils;
import com.liangli.nj.utils.Strings;
import com.liangli.nj.utils.Utils;
import com.liangli.nj.utils.WordUtil;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = Definition.getClassPath() + "/testRec/aaa.html";
		String s = new String(DeviceUtils.file.readFromFile(path));
		//System.out.println("frank " + s);
		//导出数据库数据，同时生成生字
		try {
//			exportChineseBooks2ExcelAndGenerateNewwords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//word抓取程序
//		fetchEnglishGrammarFromWordFile();
		
		//匹配改句子
//		fetchSentenceMatch();
		
//		MathPlus.mathPlus();
		
		//计算人数
//		System.out.println(CountNumberOfRegistrations.CountRegistrations(1514720419716L, 10000, 1000));
		
		//扫描目录导出excel
//		//List<String[]> FilePathCatalog = FileUtils.scanFilePathCatalog("src/main/resources/test");
//		List<String[]> FilePathCatalog = FileUtils.scanFilePathCatalog("F:/学习资料/研究生资料/春学期课件/智能优化算法课件");
//		String pathname = Definition.getClassPath() + "/FilePathCatalog.xlsx";
//		System.out.println(pathname);
//		File file = new File(pathname);
//		ExcelUtils.WriteToFile(file,"Test", FilePathCatalog);

		//数学
//		try {
//			MathManager.mathManager();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//http工具 + 读取mp3信息
//		String urlFile = main.class.getClassLoader().getResource("url.txt").getPath();
//		String outputPath = Definition.getClassPath() + "/downloadFile/";
//		List<String> urlPath = Arrays.asList(Utils.readStringFromFile(urlFile).split("\n"));
//		HttpUtils.ReadUrlAndDownloadWithType(urlPath, outputPath, "mp3");

		//读取englist_grammer_vocabulary_book
//		String json = JSON.toJSONString(ReadEngGrammarVocbook.readGrammarBookSimpleMap("course<>? and course<>? and course<>?", "grammar_basic_primary", "grammar_basic_junior", "grammar_basic_senior"));
//		System.out.println(json);
		
		//下载对应图片
//		try {
//			generateUrlAndDownloadImage();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//知识点识别
		String inputFilePath = Definition.getClassPath() + "/test.xlsx";
		KnowledgePointRecognition.handleExerciseProblems(inputFilePath);
	}
	
	private static void generateUrlAndDownloadImage() throws Exception {
		String[][] readExcel = ExcelUtils.ReadFromFile(Definition.getClassPath() + "/彩色卡片15类.xlsx");
		//String[][] readExcel = ExcelUtils.ReadFromFile(Definition.getClassPath() + "/test.xlsx");
		int index = 0;
		String outputPath = Definition.getClassPath() + "/downloadFile/";
		//String outputPath = Definition.getClassPath() + "/downloadFileTest/";
		for (int i = 0; i < readExcel[0].length; i++) {
			if (readExcel[0][i].equals("word") || 
				readExcel[0][i].equals("文件")) {
				index = i;
			}
		}
		List<String> downloadingWords = new ArrayList<>();
		List<String> allWords = new ArrayList<>();
		for (int i = 1; i < readExcel.length; i++) {
			allWords.add(readExcel[i][index]);
			
			File file = null;
			file = new File(outputPath + readExcel[i][index]);
			
			if (file.exists()) {
				//System.out.println("文件" + readExcel[i][index] + "已存在.");
			} else {
				downloadingWords.add(readExcel[i][index]);
			}
		}
		
		HttpUtils.ReadUrl2Path(HttpUtils.generateUrl(downloadingWords), outputPath, downloadingWords);
		FileInputStream inputStream = null;
		int length;  
        byte b[] = new byte[1024]; 
        
        Pattern setDataPattern = Pattern.compile("(app\\.setData\\s*\\('imgData'\\s*,\\s*)"
        		+ "[\\s\\S]*?(\\);\\s*app.setData\\s*)");
        Pattern thumbURLPattern = Pattern.compile("(\\\"thumbURL\\\":\\s*\\\").*?(\\\",)");
        
		for (String word : allWords) {
			String fileFolder = Definition.getClassPath() + File.separator + "Image" + File.separator +  word;
			//String fileFolder = Definition.getClassPath() + File.separator + "ImageTest" + File.separator +  word;
			String fileStr = "", setDataStr = "";
			
			try {
				inputStream = new FileInputStream(outputPath + word);
				while ((length = inputStream.read(b)) != -1) {  
					 fileStr += new String(b, 0, length);
				}
				Matcher matcher = setDataPattern.matcher(fileStr);
			
				if (matcher.find()) {
					setDataStr = fileStr.substring(matcher.start() + matcher.group(1).length(), matcher.end() - matcher.group(2).length() - 1);	
				}
				
				matcher = thumbURLPattern.matcher(setDataStr);
				int start = 0;
				List<String> urlStrList = new ArrayList<>();
				
				while (matcher.find(start)) {
					start = matcher.end();
					String setDataStrOk = setDataStr.substring(matcher.start() + matcher.group(1).length(), matcher.end() - matcher.group(2).length());
					urlStrList.add(setDataStrOk);
				}
				HttpUtils.ReadUrlAndDownloadWithType(urlStrList, fileFolder, "jpg", true);
			} catch (FileNotFoundException e) {
				System.out.println("读取" + outputPath + word + "失败，没有该文件！");
			}
		}
		inputStream.close();
		System.out.println("\n\ncomplete！");
	}
	
	private static void exportChineseBooks2ExcelAndGenerateNewwords() throws Exception
	{
		String path = Definition.getClassPath() + "/NewWordTable/generatefiles/";
   	 	String filename = "", lastFilename = "";
   	 	File file = null;
   	 	
   	 	Definition.printSqlInConsol = true;   	 	
   	 	List<chinese_unit> dataList = DatabaseAccessor.get().getSelect()
   	 			.select(chinese_unit.class)
   	 			.where("course=?", "yw人教版小学")
//   	 			.orderBy("bookid asc, unit asc")
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

	private static void fetchSentenceMatch()
	{
		//文件生成在target/classes/testRect目录下的
		String scanFolder = Definition.getClassPath() + "/testRec";
    	File folder = new File(scanFolder);
    	
    	List<FileBean> matchSentenceTasks = new ArrayList<>();
    	for (String filename : folder.list())
    	{
    		if (FileUtils.checkFileFormat(filename, ".xlsx")) {
    			 if (filename.equals("句型.xlsx")) {
    				 String inputPath = scanFolder + "/" + filename;
    	    			String outputPath = scanFolder + "/" + FileUtils.cutFileFormat(filename, ".xlsx")+ "match" + ".xlsx";
    	    			matchSentenceTasks.add(new FileBean(inputPath, outputPath));
				}	
    		}
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

