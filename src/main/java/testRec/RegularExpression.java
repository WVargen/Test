/**
 * 
 */
/**
 * @author vargen
 *
 */
package testRec;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.utils.WordUtil;
import com.liangli.nj.utils.ExcelUtils;


public class RegularExpression
{
    public static void main( String[] args ){
//    	String inputpath_doc = "C:/Users/Vargen/Desktop/test/2017年广东中考英语.docx";
//    	String outputpath_doc = "C:/Users/Vargen/Desktop/test/2.xlsx";
//    	File docfile = new File(outputpath_doc);
    	
    	//word文档部分
//    	String doc_read = WordUtil.readDataDocx(inputpath_doc);
//    	List<String []> doc_data = new ArrayList<>();
//    	String [] title = {"uuid","type","question","a","b","c","d","answer","explain"};
//    	doc_data.add(title);
//    	doc_data.addAll(testWordMatch.matchWord(doc_read));
//    	//System.out.println(doc_data);
//    	ExcelUtils.WriteToFile(docfile,"1", doc_data);
    	
    	String inputpath_doc = "C:/Users/vargen/Desktop/test/句型.xlsx";
    	String outputpath_doc = "C:/Users/vargen/Desktop/test/句型match.xlsx";
    	File xlsfile = new File(outputpath_doc);
    	
    	String[][] xls_read = ExcelUtils.ReadFromFile(inputpath_doc);
    	List<String []> xls_data = new ArrayList<>();
    	String [] title = {"_id","course","bookid","unitid","unitidorder","name",
    			"uuid","type","question","a","b","c","d","answer","explain","grammerid"};
    	xls_data.add(title);
    	xls_data.addAll(testXlsMatch.matchxls(xls_read));
    	
    	ExcelUtils.WriteToFile(xlsfile,"1", xls_data);
    	System.out.println("complete！");
    }
}