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
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import testJDBC.ExcelUtils;

public class RegularExpression
{
    public static void main( String[] args ){
    	String inputpath_doc = "C:/Users/Vargen/Desktop/test/2017年广东中考英语.docx";
    	String outputpath_doc = "C:/Users/Vargen/Desktop/test/2.xlsx";
    	File docfile = new File(outputpath_doc);
    	
    	//word文档部分
    	String doc_read = WordUtil.readDataDocx(inputpath_doc);
    	List<String []> doc_data = new ArrayList<>();
    	String [] title = {"uuid","type","question","a","b","c","d","answer","explain"};
    	doc_data.add(title);
    	doc_data.addAll(testWordMatch.matchWord(doc_read));
    	//System.out.println(doc_data);
    	ExcelUtils.WriteToFile(docfile,"1", doc_data);
    	
    	
    	System.out.println("complete！");
    }
}